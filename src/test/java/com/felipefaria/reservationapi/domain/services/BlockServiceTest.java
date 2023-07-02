package com.felipefaria.reservationapi.domain.services;


import com.felipefaria.reservationapi.application.web.controllers.request.BlockRequest;
import com.felipefaria.reservationapi.application.web.controllers.response.BlockResponse;
import com.felipefaria.reservationapi.application.web.exception.BusinessException;
import com.felipefaria.reservationapi.domain.gateways.BlockGateway;
import com.felipefaria.reservationapi.domain.gateways.PropertyGateway;
import com.felipefaria.reservationapi.resource.repository.jpa.BlockRepository;
import com.felipefaria.reservationapi.resource.repository.jpa.PropertyRepository;
import com.felipefaria.reservationapi.resource.repository.jpa.entities.Block;
import com.felipefaria.reservationapi.resource.repository.jpa.entities.Property;
import com.felipefaria.reservationapi.resource.repository.jpa.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class BlockServiceTest {

    @MockBean
    BlockRepository repository;

    @MockBean
    PropertyRepository propertyRepository;

    @SpyBean
    BlockGateway blockGateway;

    @SpyBean
    PropertyGateway propertyGateway;


    @Test
    void verifySuccessfulBlockCreation() {
        BlockService service = new BlockService(blockGateway, propertyGateway);
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 10);

        BlockRequest request = new BlockRequest();
        request.setPropertyId(1L);
        request.setStartDate(startDate);
        request.setEndDate(endDate);

        Block blockMock = buildBlockMock(1L, 1L, 1L, startDate, endDate);
        when(repository.save(any())).thenReturn(blockMock);
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(blockMock.getProperty()));

        BlockResponse response = service.createBlock(request);

        assertEquals(response.getId(), blockMock.getId());
        assertEquals(response.getStartDate(), startDate);
        assertEquals(response.getEndDate(), endDate);
        assertEquals(response.getProperty().getId(), 1L);
        assertEquals(response.getProperty().getName(), "PropertyMock1");
        assertEquals(response.getProperty().getOwner().getFirstName(), "User");
    }

    @Test
    void validateBlockThrowsBusinessExceptionOnCreation() {
        BlockService service = new BlockService(blockGateway, propertyGateway);
        LocalDate startDate = LocalDate.of(2023, 1, 11);
        LocalDate endDate = LocalDate.of(2023, 1, 10);

        BlockRequest request = new BlockRequest();
        request.setPropertyId(1L);
        request.setStartDate(startDate);
        request.setEndDate(endDate);

        assertThrows(BusinessException.class, () -> {
            service.createBlock(request);
        });
    }

    @Test
    void verifySuccessfulBlockDeletion() {
        BlockService service = new BlockService(blockGateway, propertyGateway);
        doNothing().when(repository).deleteById(any());
        service.deleteBlock(1L);
        verify(repository).deleteById(any());
    }

    @Test
    void verifySuccessfulBlockUpdate() {
        BlockService service = new BlockService(blockGateway, propertyGateway);
        LocalDate startDateUpdate = LocalDate.of(2023, 1, 12);
        LocalDate endDateUpdate = LocalDate.of(2023, 1, 10);

        BlockRequest request = new BlockRequest();
        request.setPropertyId(1L);
        request.setStartDate(startDateUpdate);
        request.setEndDate(endDateUpdate);

        assertThrows(BusinessException.class, () -> {
            service.updateBlock(1L, request);

        });
    }

    @Test
    void validateBlockThrowBusinessExceptionOnUpdate() {
        BlockService service = new BlockService(blockGateway, propertyGateway);
        LocalDate startDateUpdate = LocalDate.of(2023, 1, 1);
        LocalDate endDateUpdate = LocalDate.of(2023, 1, 10);

        BlockRequest request = new BlockRequest();
        request.setPropertyId(1L);
        request.setStartDate(startDateUpdate);
        request.setEndDate(endDateUpdate);

        when(propertyRepository.findById(1L)).thenReturn(Optional.of(buildPropertyMock(1L, buildUserMock(1L))));
        when(repository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            service.updateBlock(1L, request);
        });
    }


    Block buildBlockMock(Long blockId, Long userId, long propertyId, LocalDate startDate, LocalDate endDate) {
        User owner = buildUserMock(userId);
        Property property = buildPropertyMock(propertyId, owner);

        return Block.builder().id(blockId).property(property).startDate(startDate).endDate(endDate).build();
    }

    private Property buildPropertyMock(long propertyId, User owner) {
        return Property.builder()
                .id(propertyId)
                .owner(owner)
                .name("PropertyMock" + propertyId)
                .build();
    }

    private User buildUserMock(Long userId) {
        return User.builder().id(userId).firstName("User").lastName("Mock").build();
    }


}