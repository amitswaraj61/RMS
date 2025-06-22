package com.learning.rms.services.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.rms.entities.EarnCampaign;
import com.learning.rms.repositories.CampaignRepo;
import com.learning.rms.services.EarnCampaignService;

@Service
public class EarnCampaignServiceImpl implements EarnCampaignService {

	@Autowired
	private CampaignRepo campaignRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Optional<EarnCampaign> getCampaignById(String campaignId) {
		return this.campaignRepo.findById(campaignId);

	}

}
