package com.miml.epson.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.miml.epson.dto.PrinterDto;
import com.miml.epson.dto.PrinterDto.PrinterSettingResDto;
import com.miml.epson.entity.PrinterEntity;
import com.miml.epson.service.PrinterService;

@RestController
@RequestMapping("/api/printer")
public class PrinterController {
	
    private PrinterService printerService;

    public PrinterController(PrinterService printerService) {
		this.printerService = printerService;
	}

    @PostMapping("/setting")
    public PrinterSettingResDto createPrintJob(@RequestBody PrinterDto.PrinterSettingReqDto settingReqDto) throws JsonProcessingException {
    	PrinterDto.PrinterSettingResDto settingResDto = printerService.createPrintJob(settingReqDto);
    	return settingResDto;
    }

    @PostMapping("/uploadFile")
    public void uploadPrintFile(@RequestParam String uploadUri, @RequestBody byte[] fileData) {
        printerService.uploadPrintFile(uploadUri, fileData);
    }

    @PostMapping("/executeJob")
    public void executePrintJob(@RequestParam String deviceId, @RequestParam String jobId, @RequestParam String accessToken) {
        printerService.executePrintJob(deviceId, jobId, accessToken);
    }
}

