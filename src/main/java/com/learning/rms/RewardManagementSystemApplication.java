package com.learning.rms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableKafka
@EnableAsync
@SpringBootApplication
public class RewardManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(RewardManagementSystemApplication.class, args);
	}

}
