package com.felipefaria.reservationapi.domain.entities;

import com.felipefaria.reservationapi.resource.repository.jpa.entities.Property;
import lombok.Data;

import java.util.Optional;

@Data
public class PropertyDomain {
    private Long id;
    private  String name;
    private UserDomain owner;

    public PropertyDomain(Property property) {
        this.id = property.getId();
        this.name = property.getName();
        this.owner = new UserDomain(property.getOwner());
    }
}
