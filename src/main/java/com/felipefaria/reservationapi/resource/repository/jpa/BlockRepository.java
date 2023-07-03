package com.felipefaria.reservationapi.resource.repository.jpa;

import com.felipefaria.reservationapi.resource.repository.jpa.entities.BlockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

import static com.felipefaria.reservationapi.resource.repository.jpa.Query.HAS_BLOCK;

public interface BlockRepository extends JpaRepository<BlockEntity, Long> {

    List<BlockEntity> findByProperty_Id(Long propertyId);

    @Query(HAS_BLOCK)
    Boolean hasBlock(Long propertyId, LocalDate startDate, LocalDate endDate);
}
