package com.learning.rms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.rms.entities.EarnCampaign;
import java.util.Optional;
import java.util.List;

public interface CampaignRepo extends JpaRepository<EarnCampaign, Integer> {

	Optional<EarnCampaign> findByCampaignId(String campaignId);

	Optional<List<EarnCampaign>> findByEventType(String eventType);
}
