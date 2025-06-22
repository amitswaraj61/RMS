package com.learning.rms.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.rms.entities.Customers;


public interface CustomerRepo extends JpaRepository<Customers, String> {
	
	Optional<Customers> findByMobileNumber(String mobileNumber);

}
