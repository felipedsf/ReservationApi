package com.felipefaria.reservationapi.resource.repository;

import com.felipefaria.reservationapi.domain.entities.Property;
import com.felipefaria.reservationapi.domain.gateways.PropertyGateway;
import com.felipefaria.reservationapi.resource.repository.jpa.PropertyRepository;
import com.felipefaria.reservationapi.resource.repository.jpa.entities.PropertyEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor

@Repository
public class PropertyGatewayRepository implements PropertyGateway {

    private final PropertyRepository propertyRepository;

    @Override
    public Property getProperty(Long propertyId) {
        PropertyEntity propertyEntity = propertyRepository.findById(propertyId).orElseThrow();
        return new Property(propertyEntity);
    }
}
