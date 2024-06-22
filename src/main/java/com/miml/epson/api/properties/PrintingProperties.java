package com.miml.epson.api.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@ConfigurationProperties(prefix = "epson")
public class PrintingProperties {

	private String clientId;
	private String secret;
	private String hostName;

}
