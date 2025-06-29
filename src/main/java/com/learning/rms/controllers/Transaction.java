package com.learning.rms.controllers;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.learning.rms.exceptions.ResourceNotFoundException;
import com.learning.rms.kafka.KafkaProducer;
import com.learning.rms.payload.TransactionsDto;
import com.learning.rms.repositories.CampaignRepo;
import com.learning.rms.repositories.CustomerRepo;
import com.learning.rms.repositories.TransactionRepo;
import com.learning.rms.utils.CommonResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api")
public class Transaction {

	@Autowired
	private KafkaProducer kafkaProducer;
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private CampaignRepo campaignRepo;
	
	@Autowired
	private TransactionRepo transactionRepo;


	@PostMapping("/transactions")
	public CompletableFuture<ResponseEntity<?>> getTransactionById(@Valid @RequestBody TransactionsDto transactionsDto) {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime oneDayAgo = now.minusDays(1);
		LocalDateTime thirtyMinutesAhead = now.plusMinutes(30);

		LocalDateTime txnDate = transactionsDto.getTransactionDate();
		
		this.customerRepo.findByMobileNumber(transactionsDto.getMobileNumber()).orElseThrow(
				() -> new ResourceNotFoundException("Customer id not exists.. " + transactionsDto.getMobileNumber()));
		
		if (this.transactionRepo.existsByTxnRefId(transactionsDto.getTxnRefId())) {
		    CommonResponse response = new CommonResponse("TxnRefId Already Exists", false, HttpStatus.BAD_REQUEST.value());
		    return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));
		}
		
		if (transactionsDto.getCampaign() != null && transactionsDto.getCampaign().isRewardFlag()) {
			String campaignId = transactionsDto.getCampaign().getCampaignId();
			if (campaignId != null && campaignId.trim().isEmpty()) {
				throw new ResourceNotFoundException("Campaign Id is Required..");
			}
			this.campaignRepo.findById(campaignId)
					.orElseThrow(() -> new ResourceNotFoundException("Campaign id not exists.. " + campaignId));
		}

		if (txnDate.isBefore(oneDayAgo) || txnDate.isAfter(thirtyMinutesAhead)) {
			return CompletableFuture.completedFuture(
				ResponseEntity.badRequest()
					.body("Transaction date must be within 1 day in past and 30 minutes in future")
			);
		}
		return kafkaProducer.sendMessage(transactionsDto)
				.thenApply( response -> {
					  CommonResponse commonResponse = (CommonResponse) response;
					if (commonResponse.isSuccess()){
						return ResponseEntity.status(201).body(response);
					} else {
						return ResponseEntity.status(404).body(response);
					}
				});
	}
}
