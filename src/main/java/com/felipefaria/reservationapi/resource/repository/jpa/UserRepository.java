package com.felipefaria.reservationapi.resource.repository.jpa;

import com.felipefaria.reservationapi.resource.repository.jpa.entities.Booking;
import com.felipefaria.reservationapi.resource.repository.jpa.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
