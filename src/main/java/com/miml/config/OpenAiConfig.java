package com.miml.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.netty.http.client.HttpClient;

@Configuration
public class OpenAiConfig {

//    @Value("${openai.api.key}")
    private String openAiKey = "sk-proj-8w25v2T3pPFYWYf1Xar7T3BlbkFJVDaFHh8GhiwMDujg9q0s";

    @Bean
    public WebClient webClient() {
        HttpClient httpClient = HttpClient.create().wiretap(true); // 옵션 추가 가능 (로그, 타임아웃 등)
        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

        return WebClient.builder()
                .baseUrl("https://api.openai.com/v1") // ChatGPT API의 기본 URL
                .clientConnector(connector)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + openAiKey)
                .build();
    }
}
