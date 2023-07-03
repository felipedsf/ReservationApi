package com.felipefaria.reservationapi.domain.entities;

import com.felipefaria.reservationapi.resource.repository.jpa.entities.BookingEntity;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Booking {
    private Long id;
    private Property property;
    private User guest;
    private LocalDate startDate;
    private LocalDate endDate;
    private BookingStatus status;

    public Booking(BookingEntity bookingEntity) {
        this.id = bookingEntity.getId();
        this.startDate = bookingEntity.getStartDate();
        this.endDate = bookingEntity.getEndDate();
        this.guest = new User(bookingEntity.getGuest());
        this.property = new Property(bookingEntity.getProperty());
        this.status = BookingStatus.valueOf(bookingEntity.getStatus());
    }

    public Booking(Property property, User guest,LocalDate startDate, LocalDate endDate, BookingStatus status) {
        this.property = property;
        this.guest = guest;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }
}
