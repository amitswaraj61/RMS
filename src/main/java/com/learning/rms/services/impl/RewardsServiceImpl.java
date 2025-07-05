package com.learning.rms.services.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.rms.entities.Customers;
import com.learning.rms.entities.EarnCampaign;
import com.learning.rms.entities.Rewards;
import com.learning.rms.payload.TransactionsDto;
import com.learning.rms.repositories.RewardsRepo;
import com.learning.rms.services.RewardsService;

@Service
public class RewardsServiceImpl implements RewardsService {

	@Autowired
	private RewardsRepo rewardsRepo;

	@Override
	public void saveRewards(EarnCampaign earnCampaign, Customers customers, TransactionsDto transactionsDto) {
		Rewards reward = new Rewards();
		reward.setCampaignId(earnCampaign.getCampaignId());
		reward.setMobileNumber(customers.getMobileNumber());
		reward.setDate(new Date());
		reward.setTxnRefNumber(transactionsDto.getTxnRefId());
		reward.setTxnNumber(UUID.randomUUID().toString());
		reward.setRewardType(earnCampaign.getRewardCurrency());
		reward.setTxnAmount(earnCampaign.getAmount());
		reward.setPaymentCategory(earnCampaign.getPaymentCategory());
		reward.setPaymentInstrument(earnCampaign.getPaymentInstrument());
		reward.setRewardValue(earnCampaign.getRewardAmount());
		reward.setEventType(earnCampaign.getEventType());
		this.rewardsRepo.save(reward);
	}

}
