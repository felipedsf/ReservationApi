package com.felipefaria.reservationapi.application.web.controllers.response;

import com.felipefaria.reservationapi.domain.entities.Booking;
import lombok.Data;

import java.time.LocalDate;


@Data
public class BookingResponse {
    private Long id;
    private PropertyResponse property;
    private UserResponse guest;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    public BookingResponse(Booking booking) {
        this.id = booking.getId();
        this.startDate = booking.getStartDate();
        this.endDate = booking.getEndDate();
        this.guest = new UserResponse(booking.getGuest());
        this.property = new PropertyResponse(booking.getProperty());
        this.status = booking.getStatus().name();
    }
}
