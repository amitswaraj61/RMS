package com.learning.rms.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.rms.entities.Customers;
import com.learning.rms.entities.EarnCampaign;
import com.learning.rms.payload.TransactionsDto;
import com.learning.rms.repositories.CampaignRepo;
import com.learning.rms.repositories.CustomerRepo;

@Service
public class CampaignQualificationImpl {

	@Autowired
	private CampaignRepo campaignRepo;

	private static final Logger logger = LoggerFactory.getLogger(CampaignQualificationImpl.class);

	@Autowired
	private CustomerRepo customerRepo;

	
	public List<EarnCampaign> getQualifiedCampaign(TransactionsDto transactionsDto) {
		List<EarnCampaign> qualifiedCampaigns = new ArrayList<>();

		String campaignId = Optional.ofNullable(transactionsDto.getCampaign()).map(c -> c.getCampaignId())
				.map(String::toLowerCase).orElse(null);

		Optional<Customers> customerOpt = customerRepo.findByMobileNumber(transactionsDto.getMobileNumber());
		if (customerOpt.isEmpty()) {
			logger.warn("❌ Customer not found with mobile number: {}", transactionsDto.getMobileNumber());
			return qualifiedCampaigns;
		}
		Customers customer = customerOpt.get();

		if (transactionsDto.getCampaign() != null && transactionsDto.getCampaign().isRewardFlag()) {
			handleDirectCampaignMatch(transactionsDto, campaignId, customer, qualifiedCampaigns);
		} else {
			handleDynamicCampaignSearch(transactionsDto, customer, qualifiedCampaigns);
		}

		if (!qualifiedCampaigns.isEmpty()) {
			return qualifiedCampaigns;
		} else {
			logger.info("ℹ️ No qualified campaigns found for transaction.");
			return qualifiedCampaigns;
		}
	}

	private void handleDirectCampaignMatch(TransactionsDto txn, String campaignId, Customers customer,
			List<EarnCampaign> qualifiedCampaigns) {
		Optional<EarnCampaign> campaignOpt = campaignRepo.findByCampaignId(campaignId);
		if (campaignOpt.isEmpty()) {
			logger.warn("Campaign not found with ID: {}", campaignId);
			return;
		}

		EarnCampaign campaign = campaignOpt.get();
		if (matchesCampaign(txn, campaign, customer)) {
			logger.info("Campaign matched successfully: {}", campaignId);
			qualifiedCampaigns.add(campaign);
		} else {
			logger.info("Campaign {} does not match transaction conditions");
		}
	}

	private void handleDynamicCampaignSearch(TransactionsDto txn, Customers customer,
			List<EarnCampaign> qualifiedCampaigns) {
		Optional<List<EarnCampaign>> campaignsOpt = campaignRepo.findByEventType(txn.getEventType());
		if (campaignsOpt.isEmpty()) {
			logger.info("No campaigns found for event type: {}", txn.getEventType());
			return;
		}

		for (EarnCampaign campaign : campaignsOpt.get()) {
			if (matchesCampaign(txn, campaign, customer)) {
				logger.info("Campaign matched dynamically: {}", campaign.getCampaignId());
				qualifiedCampaigns.add(campaign);
			} else {
				logger.debug("Skipping campaign {} due to non-matching conditions", campaign.getCampaignId());
			}
		}
	}

	private boolean matchesCampaign(TransactionsDto txn, EarnCampaign campaign, Customers customer) {
		boolean matches = true;

		matches &= logMatch("Event Type", txn.getEventType(), campaign.getEventType());
		matches &= logMatch("Payment Category", txn.getPaymentCategory(), campaign.getPaymentCategory());
		matches &= logMatch("Payment Instrument", txn.getPaymentInstrument(), campaign.getPaymentInstrument());
		matches &= logMatch("LOB", txn.getLob(), campaign.getLOB());
		 if (txn.getAmount() >= campaign.getAmount()) {
		        logger.info("Amount is valid: Transaction amount [{}] >= Campaign amount [{}]", txn.getAmount(), campaign.getAmount());
		        matches &= true;
		    } else {
		        logger.info("Amount is too low: Transaction amount [{}] < Campaign amount [{}]", txn.getAmount(), campaign.getAmount());
		        matches &= false;
		    }
		matches &= logMatch("Customer Type", customer.getCustomerType(), campaign.getCustomerType());

		return matches;
	}

	private boolean logMatch(String fieldName, String txnValue, String campaignValue) {
		if (txnValue != null && campaignValue != null && txnValue.equalsIgnoreCase(campaignValue)) {
			logger.info("✅ {} matches: [{}]", fieldName, txnValue);
			return true;
		} else {
			logger.info("❌ {} does not match: [txn: {}, campaign: {}]", fieldName, txnValue, campaignValue);
			return false;
		}
	}
}