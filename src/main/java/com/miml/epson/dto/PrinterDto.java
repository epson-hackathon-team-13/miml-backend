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
@NoArgsConstructor
@AllArgsConstructor
public class PrinterDto {
	
	@Schema(description = "프린터 이메일")
	private String userName;
	
	@Schema(description = "Epson Connect로 발급받은 Device ID 값")
	private String subjectId;
	
	@Schema(description = "작업 별칭")
	private String jobName;
	
	@Schema(description = "문서, 사진 모드")
    private String printMode;
	
	@Schema(description = "인쇄 설정 개체, 지정하지 않으면 기본 설정 적용")
	private PrintSettingDto printSettingDto;
	
	@Schema(description = "생성된 인쇄 작업의 인쇄 작업 ID")
	private String jobId;
	
	@Schema(description = "인쇄할 파일을 업로드하기 위한 URL")
	private String uploadUrl;
	
	@Getter
	@Setter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PrinterSettingReqDto {
		
		@Schema(description = "Epson Connect 로 발급받은 Device ID 값")
		private String subjectId;
		
		@Schema(description = "작업 별칭")
		private String jobName;
		
		@Schema(description = "문서, 사진 모드")
	    private String printMode;
		
		@Schema(description = "인쇄 설정 개체, 지정하지 않으면 기본 설정 적용")
		private PrintSettingDto printSettingDto;

	}
	
	@Getter
	@Setter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PrinterSettingResDto {
		
		@Schema(description = "생성된 인쇄 작업의 인쇄 작업 ID")
		private String id;
		
		@JsonProperty("upload_uri")
		@Schema(description = "인쇄할 파일을 업로드하기 위한 URL")
		private String uploadUri;
		
	}
	
}
