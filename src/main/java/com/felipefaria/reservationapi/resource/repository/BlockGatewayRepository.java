package com.felipefaria.reservationapi.resource.repository;

import com.felipefaria.reservationapi.application.web.exception.NotFoundException;
import com.felipefaria.reservationapi.domain.entities.BlockDomain;
import com.felipefaria.reservationapi.domain.gateways.BlockGateway;
import com.felipefaria.reservationapi.resource.repository.jpa.BlockRepository;
import com.felipefaria.reservationapi.resource.repository.jpa.entities.Block;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor

@Repository
public class BlockGatewayRepository implements BlockGateway {

    private final BlockRepository repository;

    @Override
    public BlockDomain createBlock(BlockDomain blockDomain) {
        Block block = new Block(blockDomain);
        block = repository.save(block);
        return new BlockDomain(block);
    }

    @SneakyThrows(NotFoundException.class)
    @Override
    public BlockDomain updateBlock(Long blockId, BlockDomain blockDomain) {
        Block block = repository.findById(blockId).orElseThrow();
        block.setStartDate(blockDomain.getStartDate());
        block.setEndDate(blockDomain.getEndDate());
        block.getProperty().setId(blockDomain.getPropertyDomain().getId());
        return new BlockDomain(repository.save(block));
    }

    @SneakyThrows(NotFoundException.class)
    @Override
    public void deleteBlock(Long blockId) {
        repository.deleteById(blockId);
    }

    @SneakyThrows(NotFoundException.class)
    @Override
    public List<BlockDomain> listBlocks(Long propertyId) {
        List<Block> blocks = repository.findByProperty_Id(propertyId);
        List<BlockDomain> blockDomains = blocks.stream().map(BlockDomain::new).collect(Collectors.toList());
        return blockDomains;
    }
}
