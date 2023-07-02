package com.felipefaria.reservationapi.resource.repository.jpa.entities;

import com.felipefaria.reservationapi.domain.entities.BlockDomain;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "block")
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @NotNull
    @Column(name = "start_date")
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date")
    private LocalDate endDate;

    public Block(BlockDomain blockDomain) {
        this.id = blockDomain.getId();
        this.property = new Property(blockDomain.getPropertyDomain());
        this.startDate = blockDomain.getStartDate();
        this.endDate = blockDomain.getEndDate();
    }

}
