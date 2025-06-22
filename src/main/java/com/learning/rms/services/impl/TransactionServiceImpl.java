package com.learning.rms.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.learning.rms.entities.Transactions;
import com.learning.rms.exceptions.ResourceNotFoundException;
import com.learning.rms.payload.TransactionsDto;
import com.learning.rms.repositories.CampaignRepo;
import com.learning.rms.repositories.CustomerRepo;
import com.learning.rms.repositories.TransactionRepo;
import com.learning.rms.services.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepo transactionRepo;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private CampaignRepo campaignRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public TransactionsDto saveTransaction(TransactionsDto transactionsDto) {

		Transactions transactions = this.modelMapper.map(transactionsDto, Transactions.class);

		this.customerRepo.findByMobileNumber(transactionsDto.getMobileNumber()).orElseThrow(
				() -> new ResourceNotFoundException("Customer id not exists.. " + transactionsDto.getMobileNumber()));

		if (transactionsDto.getCampaign() != null && transactionsDto.getCampaign().isRewardFlag()) {
			String campaignId = transactionsDto.getCampaign().getCampaignId();
			if (campaignId != null && campaignId.trim().isEmpty()) {
				throw new ResourceNotFoundException("Campaign Id is Required..");
			}
			this.campaignRepo.findById(campaignId)
					.orElseThrow(() -> new ResourceNotFoundException("Campaign id not exists.. " + campaignId));
		}
//		this.transactionRepo.save(transactions);
//		return this.modelMapper.map(transactions, TransactionsDto.class);

		try {

			this.transactionRepo.save(transactions);
			return this.modelMapper.map(transactions, TransactionsDto.class);
		} catch (Exception e) {
			throw new ResourceNotFoundException(
					"Transactions Data is not getting save in DB.." + transactions.getTxnRefId());

		}
	}
}
