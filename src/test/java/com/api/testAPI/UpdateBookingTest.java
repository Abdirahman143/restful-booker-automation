package com.api.testAPI;

import com.api.authentication.request.AuthRequest;
import com.api.authentication.response.AuthResponse;
import com.api.baseRoute.BaseRoute;
import com.api.models.Request.BookingPartialRequest;
import com.api.models.Request.BookingRequest;
import com.api.models.Response.UpdateBookingResponse;
import com.api.utils.AuthUtils;
import com.api.utils.TestData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;
public class UpdateBookingTest {

    private static final Logger logger = LoggerFactory.getLogger(UpdateBookingTest.class);
    private UpdateBookingResponse bookingResponse;
    private BookingRequest updateBookingRequest;
    private String token ;
    private int bookingId =1;

    @BeforeClass
    public void setUp(){
        logger.info("...Bofore setUp and token generation ");

        AuthRequest authRequest = TestData.getValidLoginData();
        AuthResponse authResponse = AuthUtils.getAuthToken(authRequest.getUsername(),authRequest.getPassword());
        token = authResponse.getToken();
        updateBookingRequest = TestData.getUpdateBookingRequestData();
    }



    @Test
    public void updateBooking_WithValidData_ShouldReturnSuccess() throws JsonProcessingException {
        logger.info("Token: {}", token);
        Response response = given().
                header("Content-Type", BaseRoute.CONTENT_TYPE).
                header("Cookie", "token=" + token).
                body(updateBookingRequest).
                when().
                put(BaseRoute.BASE_URL + "/booking/" + bookingId).
                thenReturn();

        if (response.statusCode() != 200) {
            logger.error("failed to update the request.. ");
            assertThat(response.statusCode()).isEqualTo(200);
        }

        assertThat(response.statusCode()).isEqualTo(200);

        // Deserialize JSON response to BookingResponse object
        bookingResponse = new ObjectMapper().readValue(response.asString(), UpdateBookingResponse.class);
// Fluent API usage to check all fields
        assertThat(bookingResponse)
                .as("Check all booking details")
                .isNotNull()
                .satisfies(b -> {
                    assertThat(b.getFirstname()).as("Check firstname").isEqualTo(updateBookingRequest.getFirstname());
                    assertThat(b.getLastname()).as("Check lastname").isEqualTo(updateBookingRequest.getLastname());
                    assertThat(b.getTotalprice()).as("Check total price").isEqualTo(updateBookingRequest.getTotalprice());
                    assertThat(b.isDepositpaid()).as("Check deposit status").isEqualTo(updateBookingRequest.isDepositpaid());
                    assertThat(b.getBookingdates().getCheckin()).as("Check checkin date").isEqualTo(updateBookingRequest.getBookingdates().getCheckin());
                    assertThat(b.getBookingdates().getCheckout()).as("Check checkout date").isEqualTo(updateBookingRequest.getBookingdates().getCheckout());
                    assertThat(b.getAdditionalneeds()).as("Check additional needs").isEqualTo(updateBookingRequest.getAdditionalneeds());
                });

        logger.info("Booking created successfully with response payload: {}", response.asString());

    }


    @Test
    public void partial_updateBooking_WithValidData_ShouldReturnSuccess() throws JsonProcessingException {
        BookingPartialRequest partialRequest =
                BookingPartialRequest.
                builder().
                        firstname("Abdirahman").
                        lastname("Abdi").
                build();
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(partialRequest);
         int bookingId = 2;


        logger.info("Generated Token: {}",token);

        Response response = given().
                header("Content-Type",BaseRoute.CONTENT_TYPE).
                header("Cookie","token="+token)
                .body(requestBody).
                when()
                .patch(BaseRoute.BASE_URL+"/booking/"+bookingId).
                thenReturn();

        assertThat(response.statusCode()).isEqualTo(200);

        bookingResponse = new ObjectMapper().readValue(response.asString(),UpdateBookingResponse.class);

        // Fluent API usage to check all fields
        assertThat(bookingResponse)
                .as("Check all booking details")
                .isNotNull()
                .satisfies(b -> {
                    assertThat(b.getFirstname()).as("Check firstname").isEqualTo(updateBookingRequest.getFirstname());
                    assertThat(b.getLastname()).as("Check lastname").isEqualTo(updateBookingRequest.getLastname());
                });

        logger.info("Booking created successfully with response payload: {}", response.asString());
    }
}
