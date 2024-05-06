package com.api.utils;

import com.api.authentication.request.AuthRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonDataLoader {
    public static AuthRequest loadLoginData(String path) throws IOException {
        byte[] jsonData = Files.readAllBytes(Paths.get(path));
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonData, AuthRequest.class);
    }
}