package com.api.testAPI;

import com.api.authentication.request.AuthRequest;
import com.api.authentication.response.AuthResponse;
import com.api.utils.AuthUtils;
import com.api.utils.ExtentManager;
import com.api.utils.TestData;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


public class AuthTokenTest {

    private String validToken;
  public static final Logger logger =LoggerFactory.getLogger(AuthTokenTest.class);

    private ExtentReports extent;
    private ExtentTest test;
    @BeforeTest
    public void setUp(){
        extent = ExtentManager.getInstance();
    }

    @Test
    public void testGeneratedTokenValidCredential() throws IOException {
        //Test case Name:
        test = extent.createTest("Verify get token with valid credentials should generate token! ");
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
        test = extent.createTest("Verify get token with invalid credentials should not generate token! ");
        AuthRequest credentials = TestData.getInvalidLoginData();
        AuthResponse response = AuthUtils.getAuthToken(credentials.getUsername(), credentials.getPassword());
      //  logger.info("Token Reason: {}",response.getReason().toString());
        logger.info("Token Reason: {}", response.getReason());


        assertThat(response.getToken()).as("Token should be null for invalid credentials").isNull();
        assertThat(response.getReason()).as("Reason should be provided for authentication failure").isNotNull();
        assertThat(response.getReason()).as("Expected reason message did not match").isEqualTo("Bad credentials");

    }
    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "Failed: " + result.getName());
            test.log(Status.FAIL, "Cause: " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, "Skipped: " + result.getName());
        } else {
            test.log(Status.PASS, "Passed: " + result.getName());
        }
        extent.flush();
    }
}
