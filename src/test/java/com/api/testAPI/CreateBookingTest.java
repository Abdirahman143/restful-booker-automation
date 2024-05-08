package com.api.testAPI;

import com.api.authentication.request.AuthRequest;
import com.api.authentication.response.AuthResponse;
import com.api.baseRoute.BaseRoute;
import com.api.models.Request.BookingRequest;
import com.api.models.Response.CreateBookingResponse;
import com.api.utils.AuthUtils;
import com.api.utils.TestData;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;
public class CreateBookingTest {

    private String validToken;
    private static final Logger logger = LoggerFactory.getLogger(CreateBookingTest.class);
    private BookingRequest requestBody;
    private CreateBookingResponse bookingResponse;

    @BeforeClass
    public void setUp() {
        AuthRequest loginCredential = TestData.getValidLoginData();
        AuthResponse authResponse = AuthUtils.getAuthToken(loginCredential.getUsername(), loginCredential.getPassword());
        validToken = authResponse.getToken();
        logger.info("Generated Token: {}", validToken);
        requestBody = TestData.getBookingRequestData(); // Load data once if it doesn't change
    }

    @Test
    public void createBooking_WithValidDetails_ShouldReturnStatusCode200() {
        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post(BaseRoute.BASE_URL + "/booking")
                .thenReturn();

        // Check if response status is 200, if not log error and assert failure
        assertThat(response.statusCode())
                .as("Check if the server responded with 200 OK")
                .isEqualTo(200);

        bookingResponse = response.as(CreateBookingResponse.class);
        assertThat(bookingResponse)
                .as("Ensure the booking response matches expected details")
                .isNotNull()
                .satisfies(b -> {
                    assertThat(b.getBooking().getFirstname()).isEqualTo(requestBody.getFirstname());
                    assertThat(b.getBookingid()).isPositive();
                });

        logger.info("Booking created successfully with response payload: {}", response.asString());
    }

}
