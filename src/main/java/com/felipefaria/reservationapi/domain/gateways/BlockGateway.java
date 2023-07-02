package com.felipefaria.reservationapi.domain.gateways;

import com.felipefaria.reservationapi.domain.entities.BlockDomain;

import java.util.List;

public interface BlockGateway {
    BlockDomain createBlock(BlockDomain blockDomain);
    BlockDomain updateBlock(Long blockId, BlockDomain blockDomain);
    void deleteBlock(Long blockId);
    List<BlockDomain> listBlocks(Long propertyId);
}
