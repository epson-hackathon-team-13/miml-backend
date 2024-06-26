package com.miml.epson.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthenticationDto {

	@Schema(description = "고정 값 reqeust X")
	private String grant_type = "password";
	
	@Schema(description = "프린터기의 E-Mail 주소 {device_id}")
	private String username;
	
	@Schema(description = "고정 값 reqeust X")
	private String password = "";
	
	public AuthenticationDto() {}

	@Builder
	public AuthenticationDto(String username) {
		this.username = username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
