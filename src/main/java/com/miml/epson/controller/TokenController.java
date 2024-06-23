package com.miml.epson.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miml.common.api.ApiResponse;
import com.miml.epson.dto.AuthenticationDto;
import com.miml.epson.dto.PrinterDto;
import com.miml.epson.service.TokenService;

@RestController
@RequestMapping("/api/epson")
public class TokenController {
	
	private TokenService tokenService;

    public TokenController(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	@PostMapping("/authenticate")
    public ResponseEntity<ApiResponse<PrinterDto>> authenticate(@RequestBody AuthenticationDto authenticationDto) {
		
		PrinterDto printerDto = tokenService.authenticate(authenticationDto);
		
		return ApiResponse.toOkResponseEntity(printerDto);
    };
	
}
