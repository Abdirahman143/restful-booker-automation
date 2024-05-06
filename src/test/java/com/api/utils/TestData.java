package com.api.utils;

import com.api.authentication.request.AuthRequest;

import java.io.IOException;

public class TestData {
    private static final AuthRequest validLoginData;
    private static final AuthRequest invalidLoginData;

    static {
        try {
            validLoginData = JsonDataLoader.loadTestData("src/test/resources/testData/validLoginData.json",AuthRequest.class);
            invalidLoginData = JsonDataLoader.loadTestData("src/test/resources/testData/invalidLoginData.json",AuthRequest.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load login data", e);
        }
    }
    public static AuthRequest getValidLoginData() {
        return validLoginData;
    }

    public static AuthRequest getInvalidLoginData() {
        return invalidLoginData;
    }

}
