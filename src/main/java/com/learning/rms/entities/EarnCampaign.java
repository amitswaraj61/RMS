package com.learning.rms.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Setter
@Getter
@NoArgsConstructor
public class EarnCampaign {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String campaignId;

	private String campaignName;

	private Date campaignStartDate;

	private Date CampaignEndDate;

	private String customerType; // NTB(new To Bajaj), PTB(Promotional to Bajaj), ETB(Extsting to Bajaj)

	private String LOB; // (CREDIT, DEBIT CARD, HOME LOAN

	private String eventType;// 1. Not Transactional(WalletCreate/kyc) 2, Spend(Transactional (P2P, P2M,
								// BBPS)
	private String paymentCategory;

	private String paymentInstrument;

	private double amount;

	private String rewardCurrency; // Cashback, points

	private int rewardAmount;

}
