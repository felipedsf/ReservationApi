package com.felipefaria.reservationapi.resource.repository.jpa;

import com.felipefaria.reservationapi.resource.repository.jpa.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
