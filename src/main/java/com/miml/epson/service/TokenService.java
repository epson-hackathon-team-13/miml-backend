package com.miml.epson.service;


import org.springframework.stereotype.Service;

import com.miml.epson.api.client.EpsonApiClient;
import com.miml.epson.api.endPoint.EpsonApiEndPoint;
import com.miml.epson.entity.TokenInfo;
import com.miml.epson.repository.TokenInfoRepository;

@Service
public class TokenService {

    private final TokenInfoRepository tokenInfoRepository;
    private final EpsonApiClient epsonApiClient;
    private final EpsonApiEndPoint epsonApiEndPoint;

	public TokenService(
			TokenInfoRepository tokenInfoRepository, 
			EpsonApiClient epsonApiClient,
			EpsonApiEndPoint epsonApiEndPoint
			) {
		this.tokenInfoRepository = tokenInfoRepository;
		this.epsonApiClient = epsonApiClient;
		this.epsonApiEndPoint = epsonApiEndPoint;
	}
	

	public TokenInfo authenticate(String deviceId) {
		epsonApiClient.get(deviceId, null, null);
//		Class<T> responseDtoClass,
//		Map<String, String> params
		return null;
	}

}

