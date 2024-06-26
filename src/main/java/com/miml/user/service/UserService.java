package com.miml.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.miml.common.utils.PrincipalUtil;
import com.miml.security.CustomUserDetails;
import com.miml.user.dto.UserDto;
import com.miml.user.entity.UserEntity;
import com.miml.user.repository.UserRepository;

@Service
public class UserService {

	private PrincipalUtil principalUtil;
	private UserRepository userRepository;

	public UserService(PrincipalUtil principalUtil, UserRepository userRepository) {
		this.principalUtil = principalUtil;
		this.userRepository = userRepository;
	}
	
	public UserDto getUser(String email) {
		
		Optional<UserEntity> optional = userRepository.findByEmail(email);
		
		optional.orElseThrow(() -> new IllegalArgumentException(email + ":: 사용자 정보 없음"));
		UserEntity userEntity = optional.get();
		
		UserDto userDto = UserEntity.toDto(userEntity);
		
		return userDto;
	}

	public UserDto getLoggedInUser() {
		CustomUserDetails customUserDetails = (CustomUserDetails) principalUtil.getPrincipal();
		UserEntity userEntity = customUserDetails.getUser();
		
		if(userEntity == null ) {
			throw new IllegalStateException("사용자 정보 없음");
		}
		
		UserDto userDto = UserEntity.toDto(userEntity);
		
		return userDto;
	}
}
