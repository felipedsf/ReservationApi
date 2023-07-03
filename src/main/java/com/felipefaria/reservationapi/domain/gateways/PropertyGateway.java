package com.felipefaria.reservationapi.domain.gateways;

import com.felipefaria.reservationapi.domain.entities.Property;

public interface PropertyGateway {
    Property getProperty(Long propertyId);
}
