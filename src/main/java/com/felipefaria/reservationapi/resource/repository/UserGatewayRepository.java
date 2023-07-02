package com.felipefaria.reservationapi.resource.repository;

import com.felipefaria.reservationapi.domain.entities.PropertyDomain;
import com.felipefaria.reservationapi.domain.entities.UserDomain;
import com.felipefaria.reservationapi.domain.gateways.PropertyGateway;
import com.felipefaria.reservationapi.domain.gateways.UserGateway;
import com.felipefaria.reservationapi.resource.repository.jpa.PropertyRepository;
import com.felipefaria.reservationapi.resource.repository.jpa.UserRepository;
import com.felipefaria.reservationapi.resource.repository.jpa.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor

@Repository
public class UserGatewayRepository implements UserGateway {

    private final UserRepository repository;

    @Override
    public UserDomain getUserById(Long id) {
        User user = repository.findById(id).orElseThrow();
        return new UserDomain(user);
    }
}
