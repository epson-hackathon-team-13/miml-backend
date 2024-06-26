package com.miml.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miml.common.api.ApiResponse;
import com.miml.user.dto.SignUpDto;
import com.miml.user.dto.UserDto;
import com.miml.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@Operation(summary = "회원가입")
	@PostMapping("sign-up")
	public ResponseEntity<ApiResponse<UserDto>> signUp(@RequestBody SignUpDto signUpDto) {
		UserDto signedUpUserDto = userService.signUp(signUpDto);
		return ApiResponse.toOkResponseEntity(signedUpUserDto);
	} 

	@Operation(summary = "email로 유저 정보 조회")
	@GetMapping
	public ResponseEntity<ApiResponse<UserDto>> getUser(String email) {
		UserDto userDto = userService.getUser(email);
		return ApiResponse.toOkResponseEntity(userDto);
	}
	
	@Operation(summary = "로그인된 유저 정보 조회")
	@GetMapping("/logged-in")
	public ResponseEntity<ApiResponse<UserDto>> getLoggedInUser() {
		UserDto userDto = userService.getLoggedInUser();
		return ApiResponse.toOkResponseEntity(userDto);
	}

	
}
