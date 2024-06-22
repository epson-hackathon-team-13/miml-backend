package com.miml.epson.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class TokenInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clientId;
    
    private String clientSecret;
    
    private String deviceId;
    
    private String accessToken;
    
    private String refreshToken;
	
    @Builder
	public TokenInfo(Long id, String clientId, String clientSecret, String deviceId, String accessToken,
			String refreshToken) {
		this.id = id;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.deviceId = deviceId;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}    
    
    // setter
    public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

}
