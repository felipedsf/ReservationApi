package com.felipefaria.reservationapi.domain.gateways;

import com.felipefaria.reservationapi.domain.entities.User;

public interface UserGateway {
    User getUserById(Long userId);
}
