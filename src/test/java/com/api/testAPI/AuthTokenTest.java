package com.api.testAPI;

import com.api.authentication.request.AuthRequest;
import com.api.authentication.response.AuthResponse;
import com.api.utils.AuthUtils;
import com.api.utils.TestData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


public class AuthTokenTest {

    private String validToken;
  public static final Logger logger =LoggerFactory.getLogger(AuthTokenTest.class);



    @Test
    public void testGeneratedTokenValidCredential() throws IOException {
        AuthRequest credentials = TestData.getValidLoginData();
        AuthResponse authResponse = AuthUtils.getAuthToken(credentials.getUsername(), credentials.getPassword());
        validToken= authResponse.getToken();
        logger.info("Auth token generated: {}",validToken);

        assertThat(validToken).as("The token should not be null or empty when valid credentials are provided.")
                .isNotNull()
                .isNotEmpty();
    }

    @Test
    public void testInvalidCredentialsReturnReason() throws IOException {
        AuthRequest credentials = TestData.getInvalidLoginData();
        AuthResponse response = AuthUtils.getAuthToken(credentials.getUsername(), credentials.getPassword());
      //  logger.info("Token Reason: {}",response.getReason().toString());
        logger.info("Token Reason: {}", response.getReason());


        assertThat(response.getToken()).as("Token should be null for invalid credentials").isNull();
        assertThat(response.getReason()).as("Reason should be provided for authentication failure").isNotNull();
        assertThat(response.getReason()).as("Expected reason message did not match").isEqualTo("Bad credentials");

    }

}
