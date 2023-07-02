package com.felipefaria.reservationapi.domain.entities;

import com.felipefaria.reservationapi.application.web.controllers.request.BlockRequest;
import com.felipefaria.reservationapi.application.web.controllers.response.BlockResponse;
import com.felipefaria.reservationapi.resource.repository.jpa.entities.Block;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor

@Data
public class BlockDomain {

    private Long id;
    private PropertyDomain propertyDomain;
    private LocalDate startDate;
    private LocalDate endDate;

    public BlockDomain(Block block) {
        this.id = block.getId();
        this.propertyDomain = new PropertyDomain(block.getProperty());
        this.startDate = block.getStartDate();
        this.endDate = block.getEndDate();
    }

    public BlockDomain(PropertyDomain property, LocalDate startDate,LocalDate endDate) {
        this.propertyDomain = property;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
