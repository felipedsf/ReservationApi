package com.felipefaria.reservationapi.resource.repository;

import com.felipefaria.reservationapi.domain.entities.PropertyDomain;
import com.felipefaria.reservationapi.domain.gateways.PropertyGateway;
import com.felipefaria.reservationapi.resource.repository.jpa.PropertyRepository;
import com.felipefaria.reservationapi.resource.repository.jpa.entities.Property;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor

@Repository
public class PropertyGatewayRepository implements PropertyGateway {

    private final PropertyRepository propertyRepository;

    @Override
    public PropertyDomain getProperty(Long propertyId) {
        Property property = propertyRepository.findById(propertyId).orElseThrow();
        return new PropertyDomain(property);
    }
}
