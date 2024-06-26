package com.miml.word.dto;

import com.miml.common.enums.Gender;
import com.miml.user.dto.UserDto;

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
public class WordDto {

	@Schema(description = "원어 단어")
	private String word;

	@Schema(description = "번역된 단어")
	private String transWord;

	@Schema(description = "단어 설명")
	private String description;

	@Schema(description = "로그인된 사용자 정보")
	private UserDto userDto;
	
	@Schema(description = "음악 테이블 키 값")
	private Long musicId;

	@Schema(description = "아티스트 성별")
	private Gender gender;

	@Schema(description = "아티스트 이름")
	private String artist;

	@Schema(description = "음악 제목")
	private String title;

	@Schema(description = "이미지 URL")
	private String imageUrl;

}
