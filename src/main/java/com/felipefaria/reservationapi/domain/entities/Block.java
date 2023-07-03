package com.felipefaria.reservationapi.domain.entities;

import com.felipefaria.reservationapi.resource.repository.jpa.entities.BlockEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor

@Data
public class Block {

    private Long id;
    private Property property;
    private LocalDate startDate;
    private LocalDate endDate;

    public Block(BlockEntity blockEntity) {
        this.id = blockEntity.getId();
        this.property = new Property(blockEntity.getProperty());
        this.startDate = blockEntity.getStartDate();
        this.endDate = blockEntity.getEndDate();
    }
    public Block(Property property, LocalDate startDate, LocalDate endDate) {
        this.property = property;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
