package com.miml.ocr.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.miml.common.api.ApiResponse;
import com.miml.ocr.service.OcrService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ocr")
public class OcrController {

	private final OcrService ocrService;

    public OcrController(OcrService ocrService) {
        this.ocrService = ocrService;
    }

	@PostMapping("/extract")
	public ResponseEntity<ApiResponse<String>> extractText(@RequestPart("file") @Valid MultipartFile file) throws Exception {
		
		byte[] data = file.getBytes();
		String extractedText = ocrService.extractTextFromImage(data);
		return ApiResponse.toOkResponseEntity(extractedText);
	}
}
