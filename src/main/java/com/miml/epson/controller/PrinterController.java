package com.miml.epson.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miml.common.api.ApiResponse;
import com.miml.common.api.ApiResponseEmptyBody;
import com.miml.epson.dto.AuthenticationDto;
import com.miml.epson.entity.PrinterEntity;
import com.miml.epson.service.PrinterService;

@RestController
@RequestMapping("/api/printer")
public class PrinterController {
	
    private PrinterService printerService;

    public PrinterController(PrinterService printerService) {
		this.printerService = printerService;
	}

	@PostMapping("/authenticate")
    public ResponseEntity<ApiResponse<ApiResponseEmptyBody>> authenticate(@RequestBody AuthenticationDto authenticationDto) {
		
		printerService.authenticate(authenticationDto);
		
		return ApiResponse.toOkResponseEntity();
    };

    @PostMapping("/createJob")
    public PrinterEntity createPrintJob(@RequestParam String deviceId, @RequestParam String accessToken) {
        return printerService.createPrintJob(deviceId, accessToken);
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

