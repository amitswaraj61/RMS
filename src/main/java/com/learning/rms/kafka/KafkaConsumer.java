package com.learning.rms.kafka;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.learning.rms.entities.EarnCampaign;
import com.learning.rms.payload.TransactionsDto;
import com.learning.rms.services.TransactionService;
import com.learning.rms.services.impl.CampaignPrecidenceService;
import com.learning.rms.services.impl.CampaignQualificationImpl;

@Service
public class KafkaConsumer {

	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private CampaignQualificationImpl campaignQualificationImpl;
	
	@Autowired
	private CampaignPrecidenceService campaignPrecidenceService;

	@KafkaListener(topics = "Transactions", groupId = "rms-group")
	public void consume(TransactionsDto transactionsDto) {
		logger.info(String.format("Message Receive from Kafka -> %s", transactionsDto.toString()));
		CompletableFuture<Boolean> saveTransaction = transactionService.saveTransaction(transactionsDto);
		  saveTransaction.thenAccept(isTransactionSuccess -> {
		        if (isTransactionSuccess) {
		            logger.info("Transaction successfully saved for TxnRefId: " + transactionsDto.getTxnRefId());
		            List<EarnCampaign> qualifiedCampaign = campaignQualificationImpl.getQualifiedCampaign(transactionsDto);
		            if(qualifiedCampaign.size()>0) {
		            	campaignPrecidenceService.checkCampaignPrecidence(qualifiedCampaign,transactionsDto);
		            	logger.info("Calling Campaign Qualification Service");
		            }
		        } else {
		            logger.info("Failed to save transaction for TxnRefId: " + transactionsDto.getTxnRefId());
		        }
		    });
		
	}

}
