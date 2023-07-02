package com.felipefaria.reservationapi.domain.gateways;

import com.felipefaria.reservationapi.domain.entities.BookingDomain;
import com.felipefaria.reservationapi.domain.entities.UserDomain;

public interface UserGateway {
    UserDomain getUserById(Long id);
}
