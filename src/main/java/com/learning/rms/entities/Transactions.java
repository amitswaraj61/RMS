package com.learning.rms.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
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
public class Transactions {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(unique=true,nullable = false)
	private String txnRefId;

	@Column(nullable = false,length=10)
	private String mobileNumber;

	@Column(nullable = false,length=10)
	private String lob;

	@Column(nullable = false,length=20)
	private String eventType;

	@Column(nullable = false,length=20)
	private String paymentCategory;

	@Column(nullable = false,length=20)
	private String paymentInstrument;

	@Column(nullable = false,length=20)
	private double amount;

	@Column(nullable = false)
	private LocalDateTime transactionDate;

	private Campaign campaign;
}
