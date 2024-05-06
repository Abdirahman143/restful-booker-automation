package com.api.utils;

import com.api.authentication.request.AuthRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonDataLoader {

    public static <T> T loadTestData(String filePath, Class<T> type) throws Exception {
        byte[] jsonData = Files.readAllBytes(Paths.get(filePath));
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonData, type);
    }

}