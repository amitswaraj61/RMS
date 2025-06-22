package com.learning.rms.entities;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
@Embeddable
public class Campaign {

	private String campaignId;

	private boolean rewardFlag;

}
