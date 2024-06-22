package com.miml.epson.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.miml.epson.entity.TokenInfo;
import com.miml.epson.repository.TokenInfoRepository;

@Service
public class TokenService {

    @Autowired
    private TokenInfoRepository tokenInfoRepository;

    private WebClient webClient = WebClient.create();

    public TokenInfo authenticate(String clientId, String clientSecret, String deviceId) {
        // OAuth2 인증 요청 및 TokenInfo 저장
        // WebClient를 사용하여 HTTP 요청을 보냄
        String authUri = "https://xxxx.xxxxx.xxx/api/1/printing/oauth2/auth/token?subject=printer";
        TokenInfo tokenInfo = new TokenInfo();
        // 예제 코드로 실제 요청을 생략합니다.
        tokenInfo.setClientId(clientId);
        tokenInfo.setClientSecret(clientSecret);
        tokenInfo.setDeviceId(deviceId);
        tokenInfo.setAccessToken("exampleAccessToken");
        tokenInfo.setRefreshToken("exampleRefreshToken");

        tokenInfoRepository.save(tokenInfo);
        return tokenInfo;
    }
}
