package com.learning.rms.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.rms.entities.EarnCampaign;
import com.learning.rms.entities.Transactions;
import com.learning.rms.services.EarnCampaignService;

@RestController
@RequestMapping("api")
public class Transaction {
	
	@Autowired
	private EarnCampaignService earnCampaignService;

	@GetMapping("/transactions/{id}")
	public ResponseEntity<?> getTransactionById(@PathVariable String id) {
	    Optional<EarnCampaign> campaignById = earnCampaignService.getCampaignById(id);
	    
	    if (campaignById.isPresent()) {
	        return ResponseEntity.ok(campaignById.get());
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Campaign not found for ID: " + id);
	    }
	}
}
