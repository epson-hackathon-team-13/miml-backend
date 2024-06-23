package com.miml.epson.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrintSettingDto {

    @Schema(description = "용지 크기 (예: ms_a4 - A4용지)")
    private String mediaSize;

    @Schema(description = "용지 종류 (예: mt_plainpaper - )")
    private String mediaType;

    @Schema(description = "인쇄시 테두리 인쇄 설정 (예: false - 테두리 있는 인쇄)")
    private boolean borderless;

    @Schema(description = "인쇄 품질 (예: normal - 보통)")
    private String printQuality;

    @Schema(description = "급지 방법 (예: front2 - 전면 2번째 용지함)")
    private String source;

    @Schema(description = "색상 모드 (예: mono - 흑백)")
    private String colorMode;

    @Schema(description = "양면 인쇄 (예: none - 단면 인쇄)")
    private String twoSided;

    @Schema(description = "역순 인쇄 (예: false - 첫 페이지부터 인쇄)")
    private boolean reverseOrder;

    @Schema(description = "사본 인쇄 (예: 1 - 기본 설정, 1부만 인쇄)")
    private int copies;
	
}
