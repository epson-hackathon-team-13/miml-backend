package com.miml.epson.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthenticationDto {

	private String grant_type = "password";
	private String username;
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
