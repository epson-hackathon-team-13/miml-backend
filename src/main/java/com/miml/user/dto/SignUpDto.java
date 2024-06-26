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
public class SignUpDto {

	@Schema(description = "이메일 (아이디)")
	private String email;
	
	@Schema(description = "비밀번호")
	private String password;
	
	@Schema(description = "이름")
	private String username;

	@Schema(description = "닉네임(한글 이름)")
	private String nickname;

	@Schema(description = "언어")
	private String language;

	@Schema(description = "레벨")
	private Long level;
	
}
