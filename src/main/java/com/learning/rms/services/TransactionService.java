package com.learning.rms.services;

import java.util.concurrent.CompletableFuture;

import com.learning.rms.payload.TransactionsDto;

public interface TransactionService {

	CompletableFuture<Boolean> saveTransaction(TransactionsDto transactionsDto);
}
