package com.popielarski.market;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class TestUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String mapToJson(Object json){
        try {
            return objectMapper.writeValueAsString(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T mapToObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
