package com.felipefaria.reservationapi.domain.entities;

import com.felipefaria.reservationapi.resource.repository.jpa.entities.User;
import lombok.Data;

@Data
public class UserDomain {

    private final Long id;
    private final String firstName;
    private final String lastName;

    public UserDomain(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }
}
