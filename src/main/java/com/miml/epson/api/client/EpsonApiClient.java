package com.miml.epson.api.client;

import java.util.Map;
import java.util.function.Consumer;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;


@Component
public class EpsonApiClient {

	private WebClient webClient;

    public EpsonApiClient(WebClient webClient) {
        this.webClient = webClient;
    }

	public <T> T get(
			String url,
			Class<T> responseDtoClass,
			Map<String, String> params,
			Consumer<HttpHeaders> httpHeaders
			) {
		
		// 파리미터 셋팅
		LinkedMultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
        valueMap.setAll(params);
        
        // url 셋팅
		String requestUrl = UriComponentsBuilder
				.fromUriString(url)
                .queryParams(valueMap)
                .build()
                .toString();
		
        return webClient
        		.get()
        		.uri(requestUrl)
        		.headers(httpHeaders)
                .retrieve()
                .bodyToMono(responseDtoClass)
                .block();
    }

    public <T, V> T post(
    		String url, 
    		V requestDto, 
    		Class<T> responseDtoClass, 
    		Consumer<HttpHeaders> httpHeaders
    		) {
    	
        return webClient.post()
                .uri(url)
                .headers(httpHeaders)
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(responseDtoClass)
                .block();
    }
	
}
