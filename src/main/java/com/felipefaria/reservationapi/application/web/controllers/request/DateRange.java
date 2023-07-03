package com.felipefaria.reservationapi.application.web.controllers.request;

import java.time.LocalDate;

public interface DateRange {
    LocalDate getStartDate();
    LocalDate getEndDate();
}
