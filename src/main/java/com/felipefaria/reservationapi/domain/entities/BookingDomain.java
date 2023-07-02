package com.felipefaria.reservationapi.domain.entities;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingDomain {
    private Long id;
    private PropertyDomain propertyDomain;
    private UserDomain guest;
    private LocalDate startDate;
    private LocalDate endDate;

}
