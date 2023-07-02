package com.felipefaria.reservationapi.domain.gateways;

import com.felipefaria.reservationapi.domain.entities.PropertyDomain;

public interface PropertyGateway {
    PropertyDomain getProperty(Long propertyId);
}
