package com.felipefaria.reservationapi.domain.services;

import com.felipefaria.reservationapi.application.web.controllers.request.BookingRequest;
import com.felipefaria.reservationapi.application.web.controllers.request.BookingUpdateRequest;
import com.felipefaria.reservationapi.application.web.controllers.response.BookingResponse;
import com.felipefaria.reservationapi.application.web.exception.BlockOverlappingException;
import com.felipefaria.reservationapi.application.web.exception.BookingOverlappingException;
import com.felipefaria.reservationapi.domain.commons.Validations;
import com.felipefaria.reservationapi.domain.entities.Booking;
import com.felipefaria.reservationapi.domain.entities.BookingStatus;
import com.felipefaria.reservationapi.domain.entities.Property;
import com.felipefaria.reservationapi.domain.entities.User;
import com.felipefaria.reservationapi.domain.gateways.BlockGateway;
import com.felipefaria.reservationapi.domain.gateways.BookingGateway;
import com.felipefaria.reservationapi.domain.gateways.PropertyGateway;
import com.felipefaria.reservationapi.domain.gateways.UserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor

@Service
public class BookingService {

    private final BookingGateway bookingGateway;

    private final BlockGateway blockGateway;

    private final PropertyGateway propertyGateway;

    private final UserGateway userGateway;

    public BookingResponse createBooking(BookingRequest request) {
        Validations.checkDates(request);
        checkBlocks(request.getPropertyId(), request.getStartDate(), request.getEndDate());
        Validations.checkOverlapping(bookingGateway, request.getPropertyId(), request.getStartDate(), request.getEndDate());
        return new BookingResponse(bookingGateway.createBooking(getBooking(request)));
    }

    public BookingResponse updateBooking(Long bookingId, BookingUpdateRequest updateRequest) {
        Booking booking = bookingGateway.getBooking(bookingId);
        Validations.checkDates(updateRequest);
        doValidations(bookingId, booking.getProperty().getId(), updateRequest.getStartDate(), updateRequest.getEndDate());
        booking.setStartDate(updateRequest.getStartDate());
        booking.setEndDate(updateRequest.getEndDate());
        return new BookingResponse(bookingGateway.updateBooking(bookingId, booking));
    }

    public BookingResponse cancelBooking(Long bookingId) {
        Booking booking = bookingGateway.getBooking(bookingId);
        booking.setStatus(BookingStatus.CANCELED);
        return new BookingResponse(bookingGateway.updateBooking(bookingId, booking));
    }

    public BookingResponse rebooking(Long bookingId) {
        Booking booking = bookingGateway.getBookingWithStatus(bookingId, BookingStatus.CANCELED.name());
        doValidations(bookingId, booking.getProperty().getId(), booking.getStartDate(), booking.getEndDate());
        booking.setStatus(BookingStatus.REBOOKED);
        return new BookingResponse(bookingGateway.updateBooking(bookingId, booking));
    }

    public Booking getBooking(Long bookingId) {
        return bookingGateway.getBooking(bookingId);
    }

    public List<BookingResponse> listBookings(Long propertyId) {
        return bookingGateway.listBookings(propertyId).stream().map(BookingResponse::new).toList();
    }

    private Booking getBooking(BookingRequest request) {
        Property property = propertyGateway.getProperty(request.getPropertyId());
        User guest = userGateway.getUserById(request.getGuestId());
        return new Booking(property, guest, request.getStartDate(), request.getEndDate(), BookingStatus.BOOKED);
    }

    private void checkOverlapping(Long bookingId, Long propertyId, LocalDate startDate, LocalDate endDate) {
        Boolean hasOverlapping = bookingGateway.verifyBookings(bookingId, propertyId, startDate, endDate);
        if (hasOverlapping) {
            throw new BookingOverlappingException();
        }
    }

    private void checkBlocks(Long propertyId, LocalDate startDate, LocalDate endDate) {
        if (blockGateway.verifyBlocks(propertyId, startDate, endDate)) {
            throw new BlockOverlappingException();
        }
    }

    private void doValidations(Long bookingId, Long propertyId, LocalDate startDate, LocalDate endDate) {
        checkBlocks(propertyId, startDate, endDate);
        checkOverlapping(bookingId, propertyId, startDate, endDate);
    }
}
