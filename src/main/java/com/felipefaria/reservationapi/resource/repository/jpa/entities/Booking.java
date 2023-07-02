package com.felipefaria.reservationapi.resource.repository.jpa.entities;

import com.felipefaria.reservationapi.domain.entities.BookingDomain;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @ManyToOne
    @JoinColumn(name = "guest_id")
    private User guest;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    public Booking(BookingDomain booking) {
        this.id = booking.getId();
        this.property = new Property(booking.getPropertyDomain());
        this.guest = new User(booking.getGuest());
        this.startDate = booking.getStartDate();
        this.endDate = booking.getEndDate();
    }
}
