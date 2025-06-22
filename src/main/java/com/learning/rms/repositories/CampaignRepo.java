package com.learning.rms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.rms.entities.EarnCampaign;

public interface CampaignRepo extends JpaRepository<EarnCampaign, String>{

}
