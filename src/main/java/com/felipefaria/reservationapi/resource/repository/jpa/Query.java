package com.felipefaria.reservationapi.resource.repository.jpa;

final class Query {
    public final static String HAS_BLOCK = "select case when count(b) > 0 then true else false end from BlockEntity b where b.property.id = :propertyId and (b.startDate <= :endDate and b.endDate >= :startDate)";
    public final static String HAS_BOOKING = "select case when count(b) > 0 then true else false end from BookingEntity b where b.status != 'CANCELED' and b.property.id = :propertyId and (b.startDate <= :endDate and b.endDate >= :startDate)";
    public final static String HAS_BOOKING_UPDATE = "select case when count(b) > 0 then true else false end from BookingEntity b where b.status != 'CANCELED' and b.id != :bookingId and b.property.id = :propertyId and (b.startDate <= :endDate and b.endDate >= :startDate)";

}
