package com.miml.word.dto;

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
public class WordPostReqDto {

	@Schema(description = "원어 단어")
	private String word;

	@Schema(description = "번역된 단어")
	private String transWord;

	@Schema(description = "단어 설명")
	private String description;

	@Schema(description = "음악 테이블 키 값")
	private Long musicId;
	
}
