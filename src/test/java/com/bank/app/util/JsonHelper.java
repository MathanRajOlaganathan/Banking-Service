package com.bank.app.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 14/01/2021
 */
public class JsonHelper {
    public static <T> String toJson(ObjectMapper objectMapper, T object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public static <T> T fromJson(ObjectMapper objectMapper, String payload, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(payload, clazz);
    }

    public static <T> T fromJson(ObjectMapper objectMapper, String payload, TypeReference<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(payload, clazz);
    }
}
