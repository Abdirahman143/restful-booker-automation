
package com.api.models.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest{
    private String firstName;
    private String lastName;
    private int totalPrice;
    private boolean depositPaid;
    private BookingDates bookingDates;
    private String additionalNeeds;


}
