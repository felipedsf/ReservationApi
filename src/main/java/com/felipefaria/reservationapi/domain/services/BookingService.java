package com.felipefaria.reservationapi.domain.services;

import com.felipefaria.reservationapi.application.web.controllers.response.BookingResponse;
import com.felipefaria.reservationapi.domain.gateways.BookingGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor

@Service
public class BookingService {

    private final BookingGateway bookingGateway;

    public List<BookingResponse> listBookings(Long propertyId) {
//        return bookingGateway.listBookings(propertyId);
        return null;
    }
}
