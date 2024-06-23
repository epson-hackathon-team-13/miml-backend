package com.miml.ocr.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miml.common.api.ApiResponse;
import com.miml.ocr.service.OcrService;

@RestController
@RequestMapping("/api/ocr")
public class OcrController {

	private final OcrService ocrService;

    public OcrController(OcrService ocrService) {
        this.ocrService = ocrService;
    }

	@PostMapping("/extract")
	public ResponseEntity<ApiResponse<String>> extractText(@RequestParam("data") byte[] data) throws Exception {
		String extractedText = ocrService.extractTextFromImage(data);
		return ApiResponse.toOkResponseEntity(extractedText);
	}
}
