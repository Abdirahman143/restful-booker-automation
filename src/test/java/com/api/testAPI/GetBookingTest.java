package com.api.testAPI;

import com.api.baseRoute.BaseRoute;
import com.api.utils.ExtentManager;
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

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class GetBookingTest {

    private int bookingId;
    private static final Logger logger = LoggerFactory.getLogger(GetBookingTest.class);
    private ExtentReports extent;
    private ExtentTest test;

    @BeforeTest
    public void setUp() {
        extent = ExtentManager.getInstance();
        Random random = new Random();
        bookingId = random.nextInt(2000) + 1;
        logger.info("Testing with randomly generated bookingId: {}", bookingId);
    }

    @Test
    public void getBookingDetailsWithValidIdShouldReturnSuccess() {
        //Test case Name:
        test = extent.createTest("Verify get booking details with valid booking Id should return success! ");
        Response response = given()
                .baseUri(BaseRoute.BASE_URL)
                .header("Accept", BaseRoute.CONTENT_TYPE)
                .when()
                .get("/booking/13")
                .thenReturn();

        assertThat(response.statusCode())
                .as("Check that the server responded with a 200 status code")
                .isEqualTo(200);

        assertThat(response.getBody().asString())
                .as("Check that the response body is not empty or null")
                .isNotNull()
                .isNotEmpty();


        assertThat(response.jsonPath().getString("firstname"))
                .as("Validate firstName is not null")
                .isNotNull()
                .isNotEmpty();


        assertThat(response.jsonPath().getInt("totalprice"))
                .as("Validate totalPrice is a positive value")
                .isPositive();

        logger.debug("Response: {}", response.getBody().asString());
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
