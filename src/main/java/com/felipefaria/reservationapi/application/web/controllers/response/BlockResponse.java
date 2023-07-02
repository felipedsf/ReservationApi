package com.felipefaria.reservationapi.application.web.controllers.response;

import com.felipefaria.reservationapi.domain.entities.BlockDomain;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BlockResponse {
    private final Long id;
    private final PropertyResponse property;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public BlockResponse(BlockDomain block) {
        this.id = block.getId();
        this.property = new PropertyResponse(block.getPropertyDomain());
        this.startDate = block.getStartDate();
        this.endDate = block.getEndDate();
    }
}
