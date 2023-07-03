package com.felipefaria.reservationapi.resource.repository;

import com.felipefaria.reservationapi.application.web.exception.RebookingException;
import com.felipefaria.reservationapi.domain.entities.Booking;
import com.felipefaria.reservationapi.domain.gateways.BookingGateway;
import com.felipefaria.reservationapi.resource.repository.jpa.BookingRepository;
import com.felipefaria.reservationapi.resource.repository.jpa.entities.BookingEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor

@Repository
public class BookingGatewayRepository implements BookingGateway {

    private final BookingRepository bookingRepository;

    @Override
    public Booking getBooking(Long bookingId) {
        BookingEntity entity = bookingRepository.findById(bookingId).orElseThrow();
        return new Booking(entity);
    }

    @Override
    public Booking createBooking(Booking booking) {
        BookingEntity entity = new BookingEntity(booking);
        entity = bookingRepository.save(entity);
        return new Booking(entity);
    }

    @Override
    public Booking updateBooking(Long bookingId, Booking booking) {
        BookingEntity entity = bookingRepository.findById(bookingId).orElseThrow();
        entity.setStartDate(booking.getStartDate());
        entity.setEndDate(booking.getEndDate());
        entity.setStatus(booking.getStatus().name());
        entity = bookingRepository.save(entity);
        return new Booking(entity);
    }

    @Override
    public List<Booking> listBookings(Long propertyId) {
        return bookingRepository.findByProperty_Id(propertyId).stream().map(Booking::new).toList();
    }

    @Override
    public Boolean verifyBookings(Long propertyId, LocalDate startDate, LocalDate endDate) {
        return bookingRepository.hasBooking(propertyId, startDate, endDate);
    }

    @Override
    public Boolean verifyBookings(Long bookingId, Long propertyId, LocalDate startDate, LocalDate endDate) {
        return bookingRepository.hasBooking(bookingId, propertyId, startDate, endDate);
    }

    @Override
    public Booking getBookingWithStatus(Long bookingId, String status) {
        BookingEntity entity = bookingRepository.findByIdAndStatus(bookingId, status).orElseThrow(RebookingException::new);
        return new Booking(entity);
    }
}
