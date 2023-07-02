package com.felipefaria.reservationapi.application.web.controllers.response;

import com.felipefaria.reservationapi.domain.entities.UserDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {
    private final Long id;
    private final String firstName;
    private final String lastName;

    public UserResponse(UserDomain userDomain) {
        this.id = userDomain.getId();
        this.firstName = userDomain.getFirstName();
        this.lastName = userDomain.getLastName();
    }

}
