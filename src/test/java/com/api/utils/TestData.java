package com.api.utils;

import com.api.authentication.request.AuthRequest;
import com.api.models.Request.BookingRequest;

import java.io.IOException;


public class TestData {
    private static final AuthRequest validLoginData = loadValidLoginData();
    private static final AuthRequest invalidLoginData = loadInvalidLoginData();
    private static final BookingRequest bookingRequestData = loadBookingRequestData();
    private static final BookingRequest updateBookingRequestData = loadUpdateBookingRequestData();



    private static AuthRequest loadValidLoginData() {
        try {
            return JsonDataLoader.loadTestData("src/test/resources/testData/validLoginData.json", AuthRequest.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load valid login data", e);
        }
    }

    private static AuthRequest loadInvalidLoginData() {
        try {
            return JsonDataLoader.loadTestData("src/test/resources/testData/invalidLoginData.json", AuthRequest.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load invalid login data", e);
        }
    }

    private static BookingRequest loadBookingRequestData() {
        try {
            return JsonDataLoader.loadTestData("src/test/resources/testData/booking_create.json", BookingRequest.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load booking request data", e);
        }
    }

    private static BookingRequest loadUpdateBookingRequestData() {
        try {
            return JsonDataLoader.loadTestData("src/test/resources/testData/booking_update.json", BookingRequest.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load booking request data", e);
        }
    }

    public static AuthRequest getValidLoginData() {
        return validLoginData;
    }

    public static AuthRequest getInvalidLoginData() {
        return invalidLoginData;
    }

    public static BookingRequest getBookingRequestData() {
        return bookingRequestData;
    }

    public static BookingRequest getUpdateBookingRequestData() {
        return updateBookingRequestData;
    }
}