package com.miml.epson.service;


import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.miml.common.utils.PrincipalUtil;
import com.miml.epson.api.client.EpsonApiClient;
import com.miml.epson.api.endPoint.EpsonApiEndPoint;
import com.miml.epson.api.properties.PrintingProperties;
import com.miml.epson.dto.AuthenticationDto;
import com.miml.epson.dto.PrinterDto;
import com.miml.epson.entity.TokenEntity;
import com.miml.epson.repository.TokenRepository;
import com.miml.security.CustomUserDetails;
import com.miml.user.entity.UserEntity;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;
    private final PrintingProperties printingProperties;
    private final EpsonApiClient epsonApiClient;
    private final PrincipalUtil principalUtil;
 
	
	public TokenService(
			TokenRepository tokenRepository, 
			PrintingProperties printingProperties, 
			EpsonApiClient epsonApiClient, 
			PrincipalUtil principalUtil
			) {
		this.tokenRepository = tokenRepository;
		this.printingProperties = printingProperties;
		this.epsonApiClient = epsonApiClient;
		this.principalUtil = principalUtil;
	}


	public PrinterDto authenticate(AuthenticationDto authenticationDto) {
		
		String host = printingProperties.getHostName();
		String endPoint = EpsonApiEndPoint.AUTHENTICATION;
		String url = "https://" + host + endPoint;
		String Authorization = "Basic " + printingProperties.getAuth();

		MultiValueMap<String, String> formData = new LinkedMultiValueMap<String, String>();
        formData.add("grant_type", authenticationDto.getGrant_type());
        formData.add("username", authenticationDto.getUsername());
        formData.add("password", authenticationDto.getPassword());

		Consumer<HttpHeaders> requestHeader = httpHeaders -> {
		    httpHeaders.add("Authorization", Authorization);
		    httpHeaders.add("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		};
		
		TokenEntity tokenEntity = epsonApiClient.post(url, formData, TokenEntity.class, requestHeader);
		
		CustomUserDetails customUserDetails =  (CustomUserDetails) principalUtil.getPrincipal();
		UserEntity userEntity = customUserDetails.getUser();
		
		tokenEntity.setUser(userEntity);
		tokenEntity.setUsername(authenticationDto.getUsername());
		
		tokenRepository.save(tokenEntity);
		
		PrinterDto printerDto = new PrinterDto();
		printerDto.setSubjectId(tokenEntity.getSubjectId());
		
		return printerDto;
		
		// TODO: error handle / response setting
		
	}


	public List<String> findUsernameByLoggedInUser() {
		
		CustomUserDetails customUserDetails =  (CustomUserDetails) principalUtil.getPrincipal();
		UserEntity userEntity = customUserDetails.getUser();
		
		List<TokenEntity> tokenEntities = tokenRepository.findByUser(userEntity);
		 // TokenEntity 리스트에서 username을 추출하여 List<String>으로 변환
        List<String> usernames = tokenEntities.stream()
                .map(TokenEntity::getUsername)
                .collect(Collectors.toList());

        return usernames;
	}

}

