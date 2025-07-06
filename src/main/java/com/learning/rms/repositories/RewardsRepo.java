package com.learning.rms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learning.rms.entities.Rewards;

public interface RewardsRepo extends JpaRepository<Rewards, Integer> {

}
