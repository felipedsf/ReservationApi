package com.felipefaria.reservationapi.resource.repository.jpa;

import com.felipefaria.reservationapi.resource.repository.jpa.entities.Booking;
import com.felipefaria.reservationapi.resource.repository.jpa.entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
}
