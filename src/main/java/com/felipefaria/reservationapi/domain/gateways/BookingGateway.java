package com.felipefaria.reservationapi.domain.gateways;

import com.felipefaria.reservationapi.domain.entities.BookingDomain;

public interface BookingGateway {
    BookingDomain getBooking(Long bookingId);
    BookingDomain createBooking(BookingDomain bookingDomainRequest);
    BookingDomain updateBooking(Long bookingId, BookingDomain bookingDomainUpdateRequest);
    BookingDomain cancelBooking(Long bookingId);
}
