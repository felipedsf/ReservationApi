package com.felipefaria.reservationapi.application.web.controllers.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingRequest {
    @NotNull
    private Long propertyId;
    @NotNull
    private Long guestId;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
}
