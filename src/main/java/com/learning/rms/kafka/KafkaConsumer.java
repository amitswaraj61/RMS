package com.learning.rms.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.learning.rms.payload.TransactionsDto;
import com.learning.rms.services.TransactionService;

@Service
public class KafkaConsumer {

	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
	
	@Autowired
	private TransactionService transactionService;

	@KafkaListener(topics = "Transactions", groupId = "rms-group")
	public void consume(TransactionsDto transactionsDto) {
		logger.info(String.format("Message Receive from Kafka -> %s", transactionsDto.toString()));
		boolean saveTransaction = transactionService.saveTransaction(transactionsDto);
		if(saveTransaction) {
			
		}
	}
}
