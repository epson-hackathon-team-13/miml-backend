package com.miml.user.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.miml.authority.entity.AuthorityEntity;
import com.miml.authority.repository.AuthorityRepository;
import com.miml.common.api.ApiException;
import com.miml.common.api.ErrorCode;
import com.miml.common.utils.PrincipalUtil;
import com.miml.security.CustomUserDetails;
import com.miml.user.dto.SignUpDto;
import com.miml.user.dto.UserDto;
import com.miml.user.entity.UserEntity;
import com.miml.user.repository.UserRepository;

@Service
public class UserService {

	private PrincipalUtil principalUtil;
	private UserRepository userRepository;
	private AuthorityRepository authorityRepository;
	
	public UserService(PrincipalUtil principalUtil, UserRepository userRepository, AuthorityRepository authorityRepository) {
		this.principalUtil = principalUtil;
		this.userRepository = userRepository;
		this.authorityRepository = authorityRepository;
	}

	public UserDto signUp(SignUpDto signUpDto) {

        Optional<UserEntity> optional = userRepository.findByEmail(signUpDto.getEmail());

        // Optional 객체가 존재할 경우 ApiException 발생
        if (optional.isPresent()) {
        	throw new ApiException(ErrorCode.USERALREADYEXIST);
        }
        
		// 비밀번호 암호화
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encryptedPassword = passwordEncoder.encode(signUpDto.getPassword());
		
        // 존재하지 않을 경우 사용자 등록 로직 수행
        UserEntity userEntity = UserEntity.builder()
        		.email(signUpDto.getEmail())
        		.password(encryptedPassword)
        		.username(signUpDto.getUsername())
        		.nickname(signUpDto.getNickname())
        		.language(signUpDto.getLanguage())
        		.accountNonExpired(true)
    	        .accountNonLocked(true)
    	        .credentialsNonExpired(true)
        		.enabled(true)
        		.build();
        
        UserEntity signedUpEntity = userRepository.save(userEntity);
        UserDto signedUpUserDto = UserEntity.toDto(signedUpEntity);
        
        // 권한 부여
        AuthorityEntity authorityEntity = AuthorityEntity.builder()
                .authority("ROLE_USER")
                .user(signedUpEntity)
                .build();

        authorityRepository.save(authorityEntity);
        
        return signedUpUserDto;
		
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
