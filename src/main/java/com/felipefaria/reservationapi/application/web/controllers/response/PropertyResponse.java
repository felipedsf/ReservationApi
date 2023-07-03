package com.felipefaria.reservationapi.application.web.controllers.response;

import com.felipefaria.reservationapi.domain.entities.Property;
import lombok.Getter;

@Getter
public class PropertyResponse {

    private final long id;
    private final String name;
    private final UserResponse owner;

    public PropertyResponse(Property property) {
        this.id = property.getId();
        this.name = property.getName();
        this.owner = new UserResponse(property.getOwner());
    }

}
