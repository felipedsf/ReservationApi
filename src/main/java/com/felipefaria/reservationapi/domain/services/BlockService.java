package com.felipefaria.reservationapi.domain.services;

import com.felipefaria.reservationapi.application.web.controllers.request.BlockRequest;
import com.felipefaria.reservationapi.application.web.controllers.response.BlockResponse;
import com.felipefaria.reservationapi.domain.entities.BlockDomain;
import com.felipefaria.reservationapi.domain.entities.PropertyDomain;
import com.felipefaria.reservationapi.domain.gateways.BlockGateway;
import com.felipefaria.reservationapi.domain.gateways.PropertyGateway;
import com.felipefaria.reservationapi.domain.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor

@Service
public class BlockService {

    private final BlockGateway blockGateway;

    private final PropertyGateway propertyGateway;

    public BlockResponse createBlock(BlockRequest blockRequest) {
        ValidationUtils.checkDates(blockRequest);
        BlockDomain block = getBlockDomain(blockRequest);
        block = blockGateway.createBlock(block);
        return new BlockResponse(block);
    }

    public void deleteBlock(Long blockId) {
        blockGateway.deleteBlock(blockId);
    }

    public BlockResponse updateBlock(Long blockId, BlockRequest blockRequest) {
        ValidationUtils.checkDates(blockRequest);
        BlockDomain block = getBlockDomain(blockRequest);
        block = blockGateway.updateBlock(blockId, block);
        return new BlockResponse(block);
    }

    public List<BlockResponse> listBlocks(Long propertyId) {
        return blockGateway.listBlocks(propertyId).stream().map(BlockResponse::new).toList();
    }

    private BlockDomain getBlockDomain(BlockRequest blockRequest) {
        PropertyDomain property = propertyGateway.getProperty(blockRequest.getPropertyId());
        BlockDomain block = new BlockDomain(property, blockRequest.getStartDate(), blockRequest.getEndDate());
        return block;
    }


}
