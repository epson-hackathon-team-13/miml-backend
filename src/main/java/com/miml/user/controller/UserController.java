package com.miml.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miml.common.api.ApiResponse;
import com.miml.user.dto.UserDto;
import com.miml.user.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<ApiResponse<UserDto>> getUser(String email) {
		UserDto userDto = userService.getUser(email);
		return ApiResponse.toOkResponseEntity(userDto);
	}
	
	@GetMapping("/login")
	public ResponseEntity<ApiResponse<UserDto>> getLoggedInUser() {
		UserDto userDto = userService.getLoggedInUser();
		return ApiResponse.toOkResponseEntity(userDto);
	}
	
}
