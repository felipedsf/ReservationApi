package com.felipefaria.reservationapi.domain.services;

import com.felipefaria.reservationapi.resource.repository.jpa.entities.BlockEntity;
import com.felipefaria.reservationapi.resource.repository.jpa.entities.BookingEntity;
import com.felipefaria.reservationapi.resource.repository.jpa.entities.PropertyEntity;
import com.felipefaria.reservationapi.resource.repository.jpa.entities.UserEntity;

import java.time.LocalDate;

public abstract class AbstractTestBase {

    static final Long PROPERTY_ID = 2L;
    static final Long GUEST_ID = 3L;
    static final Long OWNER_ID = 1L;

    BookingEntity buildBookingMock(Long bookingId, Long ownerId, Long guestId, Long propertyId, LocalDate startDate, LocalDate endDate, String status) {
        UserEntity guest = buildUserMock(guestId);
        UserEntity owner = buildUserMock(ownerId);
        PropertyEntity propertyEntity = buildPropertyMock(propertyId, owner);

        return BookingEntity.builder().id(bookingId).property(propertyEntity).guest(guest).startDate(startDate).endDate(endDate).status(status).build();
    }

    BlockEntity buildBlockMock(Long blockId, Long userId, Long propertyId, LocalDate startDate, LocalDate endDate) {
        UserEntity owner = buildUserMock(userId);
        PropertyEntity propertyEntity = buildPropertyMock(propertyId, owner);

        return BlockEntity.builder().id(blockId).property(propertyEntity).startDate(startDate).endDate(endDate).build();
    }

    PropertyEntity buildPropertyMock(long propertyId, UserEntity owner) {
        return PropertyEntity.builder().id(propertyId).owner(owner).name("PropertyMock" + propertyId).build();
    }

    UserEntity buildUserMock(Long userId) {
        return UserEntity.builder().id(userId).firstName("User").lastName("Mock").build();
    }

}
