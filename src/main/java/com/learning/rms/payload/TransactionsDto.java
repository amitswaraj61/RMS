package com.learning.rms.payload;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.learning.rms.entities.Campaign;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
public class TransactionsDto {
	
	@Schema(hidden = true)
	private Integer id;

	@NotBlank(message = "TransactionRefId is Required")
	private String txnRefId;

	@NotBlank(message = "Mobile Number is Required")
	@Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must contain only 10 digits")
	private String mobileNumber;

	@NotBlank(message = "LOB is Required")
	private String lob;

	@NotBlank(message = "Event Type is Required")
	private String eventType;

	@NotBlank(message = "Payment Category is Required")
	private String paymentCategory;

	@NotBlank(message = "Payment Instrument is Required")
	private String paymentInstrument;

	@NotNull(message = "Amount is required")
	private double amount;

	@NotNull(message = "Check-in date is required")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Schema(description = "Transaction date in format yyyy-MM-dd HH:mm:ss", example = "2025-06-23 15:12:00")
	//@PastOrPresent(message="Transaction Date must not be in future")
	private LocalDateTime transactionDate;

	private Campaign campaign;
}
