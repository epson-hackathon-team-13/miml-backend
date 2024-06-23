package com.miml.epson.service;


import java.util.function.Consumer;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.miml.epson.api.client.EpsonApiClient;
import com.miml.epson.api.endPoint.EpsonApiEndPoint;
import com.miml.epson.api.properties.PrintingProperties;
import com.miml.epson.dto.AuthenticationDto;
import com.miml.epson.dto.PrinterDto;
import com.miml.epson.entity.TokenEntity;
import com.miml.epson.repository.TokenRepository;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;
    private final PrintingProperties printingProperties;
    private final EpsonApiClient epsonApiClient;

	
	public TokenService(TokenRepository tokenRepository, PrintingProperties printingProperties, EpsonApiClient epsonApiClient) {
		this.tokenRepository = tokenRepository;
		this.printingProperties = printingProperties;
		this.epsonApiClient = epsonApiClient;
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
		tokenEntity.setUsername(authenticationDto.getUsername());
		tokenRepository.save(tokenEntity);
		
		PrinterDto printerDto = new PrinterDto();
		printerDto.setSubjectId(tokenEntity.getSubjectId());
		
		return printerDto;
		
		// TODO: error handle / response setting
		
	}

}

