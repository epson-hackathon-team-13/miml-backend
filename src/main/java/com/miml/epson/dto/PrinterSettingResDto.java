package com.miml.epson.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

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
public class PrinterSettingResDto {

	@Schema(description = "생성된 인쇄 작업의 인쇄 작업 ID")
	private String id;
	
	@JsonProperty("upload_uri")
	@Schema(description = "인쇄할 파일을 업로드하기 위한 URL")
	private String uploadUri;
	
}
