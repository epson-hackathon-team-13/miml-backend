package com.miml.epson.api.properties;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
//@ConfigurationProperties(prefix = "epson")
public class PrintingProperties {

//	@Value("${epson.clientId}")
//	private String clientId;
//
//	@Value("${epson.secret}")
//	private String secret;
//	
//	@Value("${epson.hostName}")
//	private String hostName;
	
	private final String clientId = "2cd19d92061a453f9db4c66c48756a1f";
	private final String secret = "oX9LLlXcBwwJyhYebL48oTpsxhUgq2JyqgKpnGx5XelcO3SLQZbIQUVLxxP8UTT0";
	private final String hostName = "api.epsonconnect.com";
	
//	@ConstructorBinding
//	public PrintingProperties(String clientId, String secret, String hostName) {
//		this.clientId = clientId;
//		this.secret = secret;
//		this.hostName = hostName;
//	}
	
	public String getClientId() {
		return clientId;
	}


	public String getSecret() {
		return secret;
	}

	public String getHostName() {
		return hostName;
	}


	public String getAuth() {
		String clientId = getClientId();
		String secret = getSecret();
		return Base64.getEncoder().encodeToString((clientId + ":" + secret).getBytes(StandardCharsets.UTF_8));
	}

}
