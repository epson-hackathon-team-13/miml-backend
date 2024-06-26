package com.miml.epson.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miml.common.api.ApiResponse;
import com.miml.epson.dto.AuthenticationDto;
import com.miml.epson.dto.PrinterDto;
import com.miml.epson.service.TokenService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/epson")
public class TokenController {
	
	private TokenService tokenService;

    public TokenController(TokenService tokenService) {
		this.tokenService = tokenService;
	}

    @Operation(summary = "Epson - 토큰 발급")
	@PostMapping("/authenticate")
    public ResponseEntity<ApiResponse<PrinterDto>> authenticate(@RequestBody AuthenticationDto authenticationDto) {
		
		PrinterDto printerDto = tokenService.authenticate(authenticationDto);
		
		return ApiResponse.toOkResponseEntity(printerDto);
    };
    
    @Operation(summary = "로그인된 사용자의 Epson UserName(프린터 이메일 주소) 리스트 조회")
    @GetMapping("/usernames")
    ResponseEntity<ApiResponse<List<String>>> findUsernameByLoggedInUser() {
    	
    	List<String> usernames = tokenService.findUsernameByLoggedInUser();
    	return ApiResponse.toOkResponseEntity(usernames);
    }
    
}
