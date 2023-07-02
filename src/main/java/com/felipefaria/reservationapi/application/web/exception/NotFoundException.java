package com.felipefaria.reservationapi.application.web.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException() {
        super();
    }

    public NotFoundException(Exception e) {
        super(e);
    }
}
