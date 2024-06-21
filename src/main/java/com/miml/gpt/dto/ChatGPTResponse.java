package com.miml.gpt.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatGPTResponse {
	
    private List<Choice> choices;

    @Builder
    @Getter
    public static class Choice {
        private int index;
        private MessageDto message;

    }
}