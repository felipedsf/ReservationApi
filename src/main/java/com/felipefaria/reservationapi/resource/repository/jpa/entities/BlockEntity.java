package com.felipefaria.reservationapi.resource.repository.jpa.entities;

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
public class BlockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "property_id")
    private PropertyEntity property;

    @NotNull
    @Column(name = "start_date")
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date")
    private LocalDate endDate;

    public BlockEntity(com.felipefaria.reservationapi.domain.entities.Block block) {
        this.id = block.getId();
        this.property = new PropertyEntity(block.getProperty());
        this.startDate = block.getStartDate();
        this.endDate = block.getEndDate();
    }

}
