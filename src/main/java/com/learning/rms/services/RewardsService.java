package com.learning.rms.services;


import com.learning.rms.entities.Customers;
import com.learning.rms.entities.EarnCampaign;
import com.learning.rms.payload.TransactionsDto;

public interface RewardsService {

	void saveRewards(EarnCampaign earnCampaign,Customers customers, TransactionsDto transactionsDto);
}
