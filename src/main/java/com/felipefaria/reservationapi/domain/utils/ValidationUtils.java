package com.felipefaria.reservationapi.domain.utils;

import com.felipefaria.reservationapi.application.web.controllers.request.BlockRequest;
import com.felipefaria.reservationapi.application.web.controllers.request.BookingRequest;
import com.felipefaria.reservationapi.application.web.exception.BusinessException;

public final class ValidationUtils {

    public static void checkDates(BlockRequest request) {
        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new BusinessException("start date need's to be before end date");
        }
    }

    public static void checkDates(BookingRequest request) {
        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new BusinessException("start date need's to be before end date");
        }
    }
}
