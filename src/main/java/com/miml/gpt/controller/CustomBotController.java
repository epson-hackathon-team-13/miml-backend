package com.miml.gpt.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.miml.gpt.dto.ChatGPTRequest;
import com.miml.gpt.dto.ChatGPTResponse;

import io.swagger.v3.oas.annotations.Operation;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/gpt")
public class CustomBotController {

    private String model = "gpt-3.5-turbo";

    private String apiURL = "https://api.openai.com/v1";

    private final WebClient webClient;

    public CustomBotController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(apiURL).build();
    }

    @Operation(summary = "Chat Gpt 요청")
    @GetMapping("/chat")
    public Mono<String> chat(@RequestParam(name = "prompt") String prompt) {
        ChatGPTRequest request = new ChatGPTRequest(model, prompt);

        return webClient.post()
                .uri("")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .bodyToMono(ChatGPTResponse.class)
                .map(response -> response.getChoices().get(0).getMessage().getContent());
    }
}