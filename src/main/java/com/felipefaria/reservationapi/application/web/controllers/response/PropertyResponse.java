package com.felipefaria.reservationapi.application.web.controllers.response;

import com.felipefaria.reservationapi.domain.entities.PropertyDomain;
import lombok.Getter;

@Getter
public class PropertyResponse {

    private final long id;
    private final String name;
    private final UserResponse owner;

    public PropertyResponse(PropertyDomain propertyDomain) {
        this.id = propertyDomain.getId();
        this.name = propertyDomain.getName();
        this.owner = new UserResponse(propertyDomain.getOwner());
    }

}
