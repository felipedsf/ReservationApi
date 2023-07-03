package com.felipefaria.reservationapi.domain.entities;

import com.felipefaria.reservationapi.resource.repository.jpa.entities.PropertyEntity;
import lombok.Data;

@Data
public class Property {
    private Long id;
    private  String name;
    private User owner;

    public Property(PropertyEntity propertyEntity) {
        this.id = propertyEntity.getId();
        this.name = propertyEntity.getName();
        this.owner = new User(propertyEntity.getOwner());
    }
}
