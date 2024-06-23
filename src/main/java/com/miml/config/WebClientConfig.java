package com.miml.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.miml.epson.api.properties.PrintingProperties;

import io.netty.channel.ChannelOption;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

@Configuration
public class WebClientConfig {

	DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();

	HttpClient httpClient = HttpClient.create().option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000); // 10초
	PrintingProperties printingProperties = new PrintingProperties();
	

	@Bean
	public WebClient webClient() {
		factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
		return WebClient.builder()
				.uriBuilderFactory(factory)
				.filter(addCustomHeaders())
//				.filter(getToken())
				.codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(2 * 1024 * 1024))
				.clientConnector(new ReactorClientHttpConnector(httpClient))
				.build();
	}

	@Bean
	public ConnectionProvider connectionProvider() {
		return ConnectionProvider.builder("http-pool")
				.maxConnections(100)
				.pendingAcquireTimeout(Duration.ofMillis(0))
				.pendingAcquireMaxCount(-1)
				.maxIdleTime(Duration.ofMillis(1000L))
				.build();
	}
	
	private ExchangeFilterFunction addCustomHeaders() {
        return (clientRequest, next) -> {
            ClientRequest modifiedRequest = ClientRequest.from(clientRequest)
                    .header("X-Custom-Header", "customValue")
                    .build();
            return next.exchange(modifiedRequest);
        };
    }
	
//	private ExchangeFilterFunction getToken() {
//        return (clientRequest, next) -> next.exchange(clientRequest)
//                .flatMap(clientResponse -> {
//                	if (clientRequest.url().toString().contains(printingProperties.getHostName())
//        					&& clientResponse.statusCode().equals(HttpStatus.UNAUTHORIZED)) {
//                		
//                		
//        			}
//                    
//                    return Mono.just(clientResponse);
//                });
//    }
}
