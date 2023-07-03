package com.felipefaria.reservationapi.domain.gateways;

import com.felipefaria.reservationapi.domain.entities.Block;

import java.time.LocalDate;
import java.util.List;

public interface BlockGateway {
    Block createBlock(Block block);
    Block updateBlock(Long blockId, Block block);
    void deleteBlock(Long blockId);
    List<Block> listBlocks(Long propertyId);
    Boolean verifyBlocks(long propertyId, LocalDate startDate, LocalDate endDate);
}
