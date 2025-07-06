package com.learning.rms.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Data
public class Rewards {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String CampaignId;
	
	private String mobileNumber;
	
	private String txnRefNumber;
	
	private String txnNumber;
	
	private Date date;
	
	private String eventType;
	
	private String paymentCategory;
	
	private String paymentInstrument;
	
	private double txnAmount;
	
	private String rewardType;
	
	private double rewardValue;
		
}
