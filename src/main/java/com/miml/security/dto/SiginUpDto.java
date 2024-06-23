package com.miml.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SiginUpDto {
	
	@Schema(description = "이메일")
	private String email;
	@Schema(description = "이메일")
    private String password;

    @Builder
	public SiginUpDto(String email, String password) {
		this.email = email;
		this.password = password;
	}

}
