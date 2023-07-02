package com.felipefaria.reservationapi.resource.repository.jpa;

import com.felipefaria.reservationapi.resource.repository.jpa.entities.Block;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlockRepository extends JpaRepository<Block, Long> {
    List<Block> findByProperty_Id(Long propertyId);
}
