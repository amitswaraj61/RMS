package com.learning.rms.services.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.rms.entities.Customers;
import com.learning.rms.entities.EarnCampaign;
import com.learning.rms.payload.TransactionsDto;
import com.learning.rms.repositories.CustomerRepo;
import com.learning.rms.services.RewardsService;

@Service
public class CampaignPrecidenceService {
	
	private static final Logger logger = LoggerFactory.getLogger(CampaignPrecidenceService.class);
	
	@Autowired
	private RewardsService rewardsService;
	
	@Autowired
	private CustomerRepo customerRepo;

	public void checkCampaignPrecidence(List<EarnCampaign> qualifiedCampaigns, TransactionsDto transactionsDto) {
	    if (qualifiedCampaigns == null || qualifiedCampaigns.isEmpty()) {
	        logger.info("No qualified campaigns provided for precedence check.");
	        return;
	    }

	    Optional<Customers> customerOpt = customerRepo.findByMobileNumber(transactionsDto.getMobileNumber());
	    if (customerOpt.isEmpty()) {
	        logger.info("Customer not found with mobile number: {}", transactionsDto.getMobileNumber());
	        return;
	    }
	    Customers customer = customerOpt.get();

	    // Split campaigns by reward type
	    List<EarnCampaign> cashbackCampaigns = new ArrayList<>();
	    List<EarnCampaign> pointsCampaigns = new ArrayList<>();

	    for (EarnCampaign campaign : qualifiedCampaigns) {
	        if (campaign.getRewardCurrency() == null) continue;

	        switch (campaign.getRewardCurrency().toLowerCase()) {
	            case "cashback" -> cashbackCampaigns.add(campaign);
	            case "points" -> pointsCampaigns.add(campaign);
	        }
	    }

	    // Choose the group with precedence: cashback > points
	    List<EarnCampaign> prioritizedGroup = !cashbackCampaigns.isEmpty() ? cashbackCampaigns : pointsCampaigns;

	    if (prioritizedGroup.isEmpty()) {
	        logger.info("No campaigns with reward currency 'cashback' or 'points' found.");
	        return;
	    }

	    // Choose the campaign with the highest amount
	    EarnCampaign finalCampaign = prioritizedGroup.stream()
	        .max(Comparator.comparingInt(EarnCampaign::getRewardAmount))
	        .orElse(null);

	    if (finalCampaign != null) {
	        logger.info("🎯 Final selected campaign based on precedence and amount: ID={}, Amount={}, Currency={}",
	                finalCampaign.getCampaignId(), finalCampaign.getRewardAmount(), finalCampaign.getRewardCurrency());

	        // Process final campaign...
	        rewardsService.saveRewards(finalCampaign, customer, transactionsDto);
	    } else {
	        logger.info("Failed to determine final campaign from prioritized group.");
	    }
	}
	}
