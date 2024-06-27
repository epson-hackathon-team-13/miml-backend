package com.miml.epson.dto;

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
public class PrinterSettingReqDto {

	@Schema(description = "Epson Connect 로 발급받은 Device ID 값")
	private String subjectId;
	
	@Schema(description = "작업 별칭")
	private String jobName;
	
	@Schema(description = "문서, 사진 모드")
    private String printMode;
	
	@Schema(description = "인쇄 설정 개체, 지정하지 않으면 기본 설정 적용")
	private PrintSettingDto printSettingDto;
	
}
