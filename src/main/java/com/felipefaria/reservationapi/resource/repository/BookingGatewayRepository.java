package com.felipefaria.reservationapi.resource.repository;

import com.felipefaria.reservationapi.domain.entities.BookingDomain;
import com.felipefaria.reservationapi.domain.gateways.BookingGateway;
import com.felipefaria.reservationapi.resource.repository.jpa.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor

@Repository
public class BookingGatewayRepository implements BookingGateway {

    private final BookingRepository repository;

    @Override
    public BookingDomain getBooking(Long bookingId) {
        return null;
    }

    @Override
    public BookingDomain createBooking(BookingDomain bookingDomainRequest) {
        return null;
    }

    @Override
    public BookingDomain updateBooking(Long bookingId, BookingDomain bookingDomainUpdateRequest) {
        return null;
    }

    @Override
    public BookingDomain cancelBooking(Long bookingId) {
        return null;
    }
}
