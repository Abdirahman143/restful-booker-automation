package com.api.testAPI;

import com.api.authentication.request.AuthRequest;
import com.api.authentication.response.AuthResponse;
import com.api.baseRoute.BaseRoute;
import com.api.utils.AuthUtils;
import com.api.utils.TestData;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


   @BeforeTest
    public void setUp(){

        authRequest = TestData.getValidLoginData();
        authResponse =AuthUtils.getAuthToken(authRequest.getUsername(),authRequest.getPassword());
        token =authResponse.getToken();
    }


    @Test
    public void delete_booking_with_valid_bookingId_should_return_success(){

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
}
