package com.miml.gpt.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MessageDto {

	private String role;
	private String content;
	
	@Builder
	public MessageDto(String role, String content) {
		this.role = role;
		this.content = content;
	}
	
}