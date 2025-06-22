package com.learning.rms.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Setter
@Getter
@NoArgsConstructor
public class Transactions {

	@Id
	private String txnId;

	@Column(unique = true, nullable = false)
	private String txnRefId;

	@Column(unique = true, nullable = false)
	private String mobileNumber;

	private String lob;

	private String eventType;

	private String paymentCategory;

	private String paymentInstrument;

	private double amount;

	private Date transactionDate;

	@ManyToOne
	@JoinColumn(name = "campaign_id")
	private EarnCampaign earnCampaign;
}
