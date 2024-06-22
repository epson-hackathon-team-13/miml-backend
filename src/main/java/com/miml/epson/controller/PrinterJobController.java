package com.miml.epson.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miml.epson.entity.PrinterJob;
import com.miml.epson.entity.TokenInfo;
import com.miml.epson.service.PrinterJobService;
import com.miml.epson.service.TokenService;

@RestController
@RequestMapping("/api/printer")
public class PrinterJobController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PrinterJobService printerService;

    @PostMapping("/authenticate")
    public TokenInfo authenticate(@RequestParam String deviceId) {
        return tokenService.authenticate(deviceId);
    }

    @PostMapping("/createJob")
    public PrinterJob createPrintJob(@RequestParam String deviceId, @RequestParam String accessToken) {
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

