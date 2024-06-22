package com.miml.epson.api.client;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.miml.config.WebClientConfig;

@Component
public class EpsonApiClient {

	private final WebClientConfig webClientConfig;

	public EpsonApiClient(WebClientConfig webClientConfig) {
		this.webClientConfig = webClientConfig;
	}
	
    // WebClient 설정
//    WebClient webClient = WebClient.builder()
//            .defaultHeader(HttpHeaders.AUTHORIZATION, "Basic " + auth)
//            .build(); 
	  
	public <T> T get(
			String url,
			Class<T> responseDtoClass,
			Map<String, String> params
			) {
		// params
		LinkedMultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
        valueMap.setAll(params);
        
		String requestUrl = UriComponentsBuilder.fromUriString(url)
                .queryParams(valueMap)
                .build()
                .toString();
		
        return webClientConfig.webClient().method(HttpMethod.GET)
                .uri(requestUrl)
                .retrieve()
                .bodyToMono(responseDtoClass)
                .block();
    }

    public <T, V> T post(String url, V requestDto, Class<T> responseDtoClass) {
        return webClientConfig.webClient().method(HttpMethod.POST)
                .uri(url)
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(responseDtoClass)
                .block();
    }
	
}
