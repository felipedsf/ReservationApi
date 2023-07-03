package com.felipefaria.reservationapi.domain.gateways;

import com.felipefaria.reservationapi.domain.entities.Booking;

import java.time.LocalDate;
import java.util.List;

public interface BookingGateway {
    Booking getBooking(Long bookingId);
    Booking createBooking(Booking booking);
    Booking updateBooking(Long bookingId, Booking booking);
    List<Booking> listBookings(Long propertyId);
    Boolean verifyBookings(Long propertyId, LocalDate startDate, LocalDate endDate);
    Boolean verifyBookings(Long bookingId, Long propertyId, LocalDate startDate, LocalDate endDate);
    Booking getBookingWithStatus(Long bookingId, String status);
}
