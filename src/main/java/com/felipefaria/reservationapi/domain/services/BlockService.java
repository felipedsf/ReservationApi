package com.felipefaria.reservationapi.domain.services;

import com.felipefaria.reservationapi.application.web.controllers.request.BlockRequest;
import com.felipefaria.reservationapi.application.web.controllers.response.BlockResponse;
import com.felipefaria.reservationapi.domain.commons.Validations;
import com.felipefaria.reservationapi.domain.entities.Block;
import com.felipefaria.reservationapi.domain.entities.Property;
import com.felipefaria.reservationapi.domain.gateways.BlockGateway;
import com.felipefaria.reservationapi.domain.gateways.BookingGateway;
import com.felipefaria.reservationapi.domain.gateways.PropertyGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor

@Service
public class BlockService {

    private final BlockGateway blockGateway;
    private final BookingGateway bookingGateway;
    private final PropertyGateway propertyGateway;

    public BlockResponse createBlock(BlockRequest blockRequest) {
        doValidations(blockRequest);

        Block block = getBlock(blockRequest);
        block = blockGateway.createBlock(block);
        return new BlockResponse(block);
    }

    public void deleteBlock(Long blockId) {
        blockGateway.deleteBlock(blockId);
    }

    public BlockResponse updateBlock(Long blockId, BlockRequest blockRequest) {
        doValidations(blockRequest);

        Block block = getBlock(blockRequest);
        block = blockGateway.updateBlock(blockId, block);
        return new BlockResponse(block);
    }
    public List<BlockResponse> listBlocks(Long propertyId) {
        return blockGateway.listBlocks(propertyId).stream().map(BlockResponse::new).toList();
    }
    private Block getBlock(BlockRequest blockRequest) {
        Property property = propertyGateway.getProperty(blockRequest.getPropertyId());
        Block block = new Block(property, blockRequest.getStartDate(), blockRequest.getEndDate());
        return block;
    }
    private void doValidations(BlockRequest blockRequest) {
        Validations.checkDates(blockRequest);
        Validations.checkOverlapping(bookingGateway, blockRequest.getPropertyId(), blockRequest.getStartDate(), blockRequest.getEndDate());
    }
}
