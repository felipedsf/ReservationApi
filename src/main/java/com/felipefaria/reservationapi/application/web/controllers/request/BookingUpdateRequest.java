package com.felipefaria.reservationapi.application.web.controllers.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingUpdateRequest implements DateRange {
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
}
