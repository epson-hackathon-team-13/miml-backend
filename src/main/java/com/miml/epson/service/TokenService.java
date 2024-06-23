package com.miml.epson.service;


import org.springframework.stereotype.Service;

import com.miml.epson.api.client.EpsonApiClient;
import com.miml.epson.api.endPoint.EpsonApiEndPoint;
import com.miml.epson.repository.TokenRepository;

@Service
public class TokenService {

    private final TokenRepository tokenInfoRepository;
    private final EpsonApiClient epsonApiClient;
    private final EpsonApiEndPoint epsonApiEndPoint;

	public TokenService(
			TokenRepository tokenInfoRepository, 
			EpsonApiClient epsonApiClient,
			EpsonApiEndPoint epsonApiEndPoint
			) {
		this.tokenInfoRepository = tokenInfoRepository;
		this.epsonApiClient = epsonApiClient;
		this.epsonApiEndPoint = epsonApiEndPoint;
	}
	



}

