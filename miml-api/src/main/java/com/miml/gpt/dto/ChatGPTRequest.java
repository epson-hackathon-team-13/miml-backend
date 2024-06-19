package com.miml.gpt.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatGPTRequest {

	private String model;
    private List<MessageDto> messages;

    @Builder
    public ChatGPTRequest(String model, String prompt) {
        this.model = model;
        this.messages =  new ArrayList<>();
        this.messages.add(new MessageDto("user", prompt));
    };

}
