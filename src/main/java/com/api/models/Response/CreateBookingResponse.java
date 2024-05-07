package com.api.models.Response;

import com.api.models.Request.BookingRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateBookingResponse {
    @JsonProperty("bookingid")
    private int bookingid;
    private BookingRequest booking;


}
