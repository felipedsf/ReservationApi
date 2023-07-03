package com.felipefaria.reservationapi.resource.repository;

import com.felipefaria.reservationapi.application.web.exception.NotFoundException;
import com.felipefaria.reservationapi.domain.entities.Block;
import com.felipefaria.reservationapi.domain.gateways.BlockGateway;
import com.felipefaria.reservationapi.resource.repository.jpa.BlockRepository;
import com.felipefaria.reservationapi.resource.repository.jpa.entities.BlockEntity;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor

@Repository
public class BlockGatewayRepository implements BlockGateway {

    private final BlockRepository blockRepository;

    @Override
    public Block createBlock(Block block) {
        BlockEntity blockEntity = new BlockEntity(block);
        blockEntity = blockRepository.save(blockEntity);
        return new Block(blockEntity);
    }

    @SneakyThrows(NotFoundException.class)
    @Override
    public Block updateBlock(Long blockId, Block block) {
        BlockEntity blockEntity = blockRepository.findById(blockId).orElseThrow();
        blockEntity.setStartDate(block.getStartDate());
        blockEntity.setEndDate(block.getEndDate());
        blockEntity.getProperty().setId(block.getProperty().getId());
        return new Block(blockRepository.save(blockEntity));
    }

    @SneakyThrows(NotFoundException.class)
    @Override
    public void deleteBlock(Long blockId) {
        blockRepository.deleteById(blockId);
    }

    @SneakyThrows(NotFoundException.class)
    @Override
    public List<Block> listBlocks(Long propertyId) {
        List<BlockEntity> blockEntities = blockRepository.findByProperty_Id(propertyId);
        List<Block> blockDomains = blockEntities.stream().map(Block::new).collect(Collectors.toList());
        return blockDomains;
    }

    @Override
    public Boolean verifyBlocks(long propertyId, LocalDate startDate, LocalDate endDate) {
        return blockRepository.hasBlock(propertyId, startDate, endDate);
    }
}
