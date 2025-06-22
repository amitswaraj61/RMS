package com.learning.rms.services;

import com.learning.rms.payload.TransactionsDto;

public interface TransactionService {

	TransactionsDto saveTransaction(TransactionsDto transactionsDto);
}
