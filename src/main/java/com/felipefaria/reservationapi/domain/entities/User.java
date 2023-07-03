package com.felipefaria.reservationapi.domain.entities;

import com.felipefaria.reservationapi.resource.repository.jpa.entities.UserEntity;
import lombok.Data;

@Data
public class User {

    private final Long id;
    private final String firstName;
    private final String lastName;

    public User(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.firstName = userEntity.getFirstName();
        this.lastName = userEntity.getLastName();
    }
}
