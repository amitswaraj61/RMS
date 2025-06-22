package com.learning.rms.services;

import java.util.Optional;

import com.learning.rms.entities.EarnCampaign;

public interface EarnCampaignService {

	Optional<EarnCampaign>  getCampaignById(String campaignId);
}
