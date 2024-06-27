package com.miml.epson.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.miml.common.api.ApiResponse;
import com.miml.epson.service.PrinterService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/epson")
public class PrinterController {
	
    private PrinterService printerService;

    public PrinterController(PrinterService printerService) {
		this.printerService = printerService;
	}

//    @Operation(summary = "Epson - 프린트 옵션 셋팅 | step 2")
//    @PostMapping("/setting")
//    public PrinterSettingResDto settingPrint(@RequestBody PrinterSettingReqDto settingReqDto) throws JsonProcessingException {
//    	PrinterSettingResDto settingResDto = printerService.settingPrint(settingReqDto);
//    	return settingResDto;
//    }
	
    @Operation(summary = "Epson - 프린트 출력 | step 2 ~ 4")
    @PostMapping("/print")
    public ResponseEntity<ApiResponse<String>> uploadToExcute(
            @RequestPart("file") MultipartFile file,
            @RequestPart("username") String username
    		) throws Exception {
    	printerService.uploadToExcute(username, file);
        return ApiResponse.toOkResponseEntity("Print job executed successfully.");
    }

}

