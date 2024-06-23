package com.miml.common.utils;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
    
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    // List<String> -> JSON String
    public static String listToJson(List<String> list) throws JsonProcessingException {
        return objectMapper.writeValueAsString(list);
    }
    
    // JSON String -> List<String>
    public static List<String> jsonToList(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, new TypeReference<List<String>>() {});
    }
    
    // List<Long> -> JSON String
    public static String longListToJson(List<String> list) throws JsonProcessingException {
        return String.join(",",list);
    }

    // JSON String -> List<Long>
    public static List<String> jsonToLongList(String json) throws JsonProcessingException {
        return Arrays.asList(json.split(","));
    }
}