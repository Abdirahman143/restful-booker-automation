package com.api.models.Response;

import com.api.models.Request.BookingRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    private int bookingId;
    private BookingRequest booking;
}
