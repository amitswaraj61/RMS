package com.learning.rms.entities;

import jakarta.persistence.Entity;
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
public class Customers {

	@Id
	private String customerId;
	
	private String mobileNumber;
	
	private String userEmail;
	
	private String customerType;
	
}
