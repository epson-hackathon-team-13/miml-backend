package com.miml.epson.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.miml.common.api.ApiResponse;
import com.miml.epson.dto.PrinterDto;
import com.miml.epson.dto.PrinterDto.PrinterSettingResDto;
import com.miml.epson.service.PrinterService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/epson")
public class PrinterController {
	
    private PrinterService printerService;

    public PrinterController(PrinterService printerService) {
		this.printerService = printerService;
	}

    @Operation(summary = "Epson - 프린트 옵션 셋팅 | step 2")
    @PostMapping("/setting")
    public PrinterSettingResDto settingPrint(@RequestBody PrinterDto.PrinterSettingReqDto settingReqDto) throws JsonProcessingException {
    	PrinterDto.PrinterSettingResDto settingResDto = printerService.settingPrint(settingReqDto);
    	return settingResDto;
    }
	
    @Operation(summary = "Epson - 프린트 출력 | step 2 ~ 4")
    @PostMapping("/print")
    public ResponseEntity<ApiResponse<String>> uploadPrintFile(
            @RequestPart("file") MultipartFile file
    		) throws Exception {
    	
    	PrinterDto printerDto = new PrinterDto();
    	printerDto.setUserName("vyg9426520eip4@print.epsonconnect.com");
    	printerDto.setJobName("TestSample2");
    	printerDto.setPrintMode("document");
    	printerDto.setSubjectId("5f3356c9461d481582719d5304d9603d");
    	
    	
    	printerService.uploadPrintFile(printerDto, file);
        return ApiResponse.toOkResponseEntity("Print job executed successfully.");
    }

}

