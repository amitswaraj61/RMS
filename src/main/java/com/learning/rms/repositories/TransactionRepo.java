package com.learning.rms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.rms.entities.Transactions;

public interface TransactionRepo extends JpaRepository<Transactions, Integer> {
	
	boolean existsByTxnRefId(String txnRefId);

}
