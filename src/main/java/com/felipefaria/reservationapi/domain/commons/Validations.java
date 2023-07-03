package com.felipefaria.reservationapi.domain.commons;

import com.felipefaria.reservationapi.application.web.controllers.request.DateRange;
import com.felipefaria.reservationapi.application.web.exception.BookingOverlappingException;
import com.felipefaria.reservationapi.application.web.exception.DateRangeException;
import com.felipefaria.reservationapi.domain.gateways.BookingGateway;

import java.time.LocalDate;

public final class Validations {
    public static void checkDates(DateRange request) {
        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new DateRangeException("start date need's to be before end date");
        }
    }

    public static void checkOverlapping(BookingGateway bookingGateway, Long propertyId, LocalDate startDate, LocalDate endDate) {
        Boolean hasOverlapping = bookingGateway.verifyBookings(propertyId, startDate, endDate);
        if (hasOverlapping) {
            throw new BookingOverlappingException();
        }
    }

}
