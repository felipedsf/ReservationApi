package com.felipefaria.reservationapi.resource.repository.jpa.entities;

import com.felipefaria.reservationapi.domain.entities.Booking;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "booking")
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "property_id")
    private PropertyEntity property;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "guest_id")
    private UserEntity guest;

    @NotNull
    @Column(name = "start_date")
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date")
    private LocalDate endDate;

    @NotNull
    @Column(name = "status")
    private String status;

    public BookingEntity(Booking booking) {
        this.id = booking.getId();
        this.property = new PropertyEntity(booking.getProperty());
        this.guest = new UserEntity(booking.getGuest());
        this.startDate = booking.getStartDate();
        this.endDate = booking.getEndDate();
        this.status = booking.getStatus().name();
    }
}
