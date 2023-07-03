package com.felipefaria.reservationapi.resource.repository;

import com.felipefaria.reservationapi.domain.entities.User;
import com.felipefaria.reservationapi.domain.gateways.UserGateway;
import com.felipefaria.reservationapi.resource.repository.jpa.UserRepository;
import com.felipefaria.reservationapi.resource.repository.jpa.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor

@Repository
public class UserGatewayRepository implements UserGateway {

    private final UserRepository repository;

    @Override
    public User getUserById(Long userId) {
        UserEntity userEntity = repository.findById(userId).orElseThrow();
        return new User(userEntity);
    }
}
