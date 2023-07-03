package com.felipefaria.reservationapi.resource.repository.jpa;

import com.felipefaria.reservationapi.resource.repository.jpa.entities.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.felipefaria.reservationapi.resource.repository.jpa.Query.HAS_BOOKING;
import static com.felipefaria.reservationapi.resource.repository.jpa.Query.HAS_BOOKING_UPDATE;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    List<BookingEntity> findByProperty_Id(Long propertyId);

    Optional<BookingEntity> findByIdAndStatus(Long bookingId, String status);

    @Query(HAS_BOOKING)
    Boolean hasBooking(Long propertyId, LocalDate startDate, LocalDate endDate);

    @Query(HAS_BOOKING_UPDATE)
    Boolean hasBooking(Long bookingId, Long propertyId, LocalDate startDate, LocalDate endDate);


}
