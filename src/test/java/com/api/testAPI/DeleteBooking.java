package com.api.testAPI;

import com.api.authentication.request.AuthRequest;
import com.api.authentication.response.AuthResponse;
import com.api.baseRoute.BaseRoute;
import com.api.utils.AuthUtils;
import com.api.utils.ExtentManager;
import com.api.utils.TestData;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Random;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;

public class DeleteBooking {

    private String token;
    private static final Logger logger = LoggerFactory.getLogger(DeleteBooking.class);
    private AuthRequest authRequest;
    private AuthResponse authResponse;
    private int bookingId;
    private ExtentReports extent;
    private ExtentTest test;


   @BeforeTest
    public void setUp(){
       extent = ExtentManager.getInstance();
        authRequest = TestData.getValidLoginData();
        authResponse =AuthUtils.getAuthToken(authRequest.getUsername(),authRequest.getPassword());
        token =authResponse.getToken();
    }


    @Test
    public void delete_booking_with_valid_bookingId_should_return_success(){
        //Test case Name:
        test = extent.createTest("Verify delete booking details with valid booking Id should return success! ");

        Random random = new Random();
        bookingId = random.nextInt(1500)+1;
        logger.info("Booking Id: {}",bookingId);
       logger.info("Token generated : {}",token);
        Response response = given().
                baseUri(BaseRoute.BASE_URL).
                header("Content-Type", BaseRoute.CONTENT_TYPE).
                header("Cookie","token="+token).
                when().
                delete("/booking/"+bookingId);


        assertThat(response.statusCode()).isEqualTo(201);
        logger.info("Full response after deletion: {}",response.asString());
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
