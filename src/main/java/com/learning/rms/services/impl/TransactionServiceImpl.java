package com.learning.rms.services.impl;

import java.util.concurrent.CompletableFuture;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.learning.rms.entities.Transactions;
import com.learning.rms.payload.TransactionsDto;
import com.learning.rms.repositories.TransactionRepo;
import com.learning.rms.services.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepo transactionRepo;

	private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

	@Autowired
	private ModelMapper modelMapper;

	public CompletableFuture<Boolean> saveTransaction(TransactionsDto transactionsDto) {
		Transactions transactions = this.modelMapper.map(transactionsDto, Transactions.class);
		try {
			Transactions save = this.transactionRepo.save(transactions);
			logger.info("✅ Transaction successfully inserted: " + save.getTxnRefId());
			return CompletableFuture.completedFuture(true);
		} catch (Exception e) {
			logger.error("❌ Transaction failed to insert: " + transactionsDto.getTxnRefId(), e);
			return CompletableFuture.completedFuture(false);
		}
	}
}
