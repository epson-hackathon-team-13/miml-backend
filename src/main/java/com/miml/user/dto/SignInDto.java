package com.miml.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInDto {

	@Schema(description = "유저 이메일(아이디)")
	private String email;
	
	@Schema(description = "비밀번호")
	private String password;
	
}