package com.miml.epson.api.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "epson")
public class PrintingProperties {

    private String clientId;
    private String secret;
    private String hostName;
    
	public String getClientId() {
		return clientId;
	}
	public String getSecret() {
		return secret;
	}
	public String getHostName() {
		return hostName;
	}
	
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

}
