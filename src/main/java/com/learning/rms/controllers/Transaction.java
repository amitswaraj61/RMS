package com.learning.rms.controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.rms.payload.TransactionsDto;
import com.learning.rms.services.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api")
public class Transaction {

	@Autowired
	private TransactionService transactionService;

	@PostMapping("/transactions")
	public ResponseEntity<?> getTransactionById(@Valid @RequestBody TransactionsDto transactionsDto) {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime oneDayAgo = now.minusDays(1);
		LocalDateTime thirtyMinutesAhead = now.plusMinutes(30);

		LocalDateTime txnDate = transactionsDto.getTransactionDate();

		if (txnDate.isBefore(oneDayAgo) || txnDate.isAfter(thirtyMinutesAhead)) {
			return ResponseEntity.badRequest()
					.body("Transaction date must be within 1 day in past and 30 minutes in future");
		}
		TransactionsDto saveTransaction = transactionService.saveTransaction(transactionsDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(saveTransaction);
	}
}
