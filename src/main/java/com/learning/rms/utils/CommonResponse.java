package com.learning.rms.utils;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class CommonResponse {


	private String message;
	private int status;
	private boolean success;

	public CommonResponse(String message, boolean success, int status) {
		this.message = message;
		this.success = success;
		this.status = status;
	}
}
