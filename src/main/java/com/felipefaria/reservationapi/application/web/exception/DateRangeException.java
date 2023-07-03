package com.felipefaria.reservationapi.application.web.exception;

public class DateRangeException extends RuntimeException{
    public DateRangeException(String message) {
        super(message);
    }
}
