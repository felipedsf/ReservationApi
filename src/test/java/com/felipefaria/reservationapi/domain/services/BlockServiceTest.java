package com.felipefaria.reservationapi.domain.services;


import com.felipefaria.reservationapi.application.web.controllers.request.BlockRequest;
import com.felipefaria.reservationapi.application.web.controllers.response.BlockResponse;
import com.felipefaria.reservationapi.application.web.exception.BookingOverlappingException;
import com.felipefaria.reservationapi.application.web.exception.DateRangeException;
import com.felipefaria.reservationapi.domain.gateways.BlockGateway;
import com.felipefaria.reservationapi.domain.gateways.BookingGateway;
import com.felipefaria.reservationapi.domain.gateways.PropertyGateway;
import com.felipefaria.reservationapi.resource.repository.jpa.BlockRepository;
import com.felipefaria.reservationapi.resource.repository.jpa.BookingRepository;
import com.felipefaria.reservationapi.resource.repository.jpa.PropertyRepository;
import com.felipefaria.reservationapi.resource.repository.jpa.entities.BlockEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class BlockServiceTest extends AbstractTestBase {

    @MockBean
    BookingRepository bookingRepository;
    @MockBean
    BlockRepository blockRepository;
    @MockBean
    PropertyRepository propertyRepository;

    @SpyBean
    BlockGateway blockGateway;
    @SpyBean
    BookingGateway bookingGateway;
    @SpyBean
    PropertyGateway propertyGateway;

    BlockService service;

    @BeforeEach
    void setUp() {
        service = new BlockService(blockGateway, bookingGateway, propertyGateway);
    }

    @Test
    void verifySuccessfulBlockCreation() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 10);

        BlockRequest request = new BlockRequest();
        request.setPropertyId(1L);
        request.setStartDate(startDate);
        request.setEndDate(endDate);

        BlockEntity blockMock = buildBlockMock(1L, 1L, 1L, startDate, endDate);

        when(blockRepository.hasBlock(any(), any(), any())).thenReturn(false);
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(blockMock.getProperty()));
        when(blockRepository.save(any())).thenReturn(blockMock);

        BlockResponse response = service.createBlock(request);

        assertEquals(response.getId(), blockMock.getId());
        assertEquals(response.getStartDate(), startDate);
        assertEquals(response.getEndDate(), endDate);
        assertEquals(response.getProperty().getId(), 1L);
        assertEquals(response.getProperty().getName(), "PropertyMock1");
        assertEquals(response.getProperty().getOwner().getFirstName(), "User");
    }


    @Test
    void validateBlockThrowsBookingOverlappingExceptionOnCreation() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 10);

        BlockRequest request = new BlockRequest();
        request.setPropertyId(1L);
        request.setStartDate(startDate);
        request.setEndDate(endDate);

        when(bookingRepository.hasBooking(any(), any(), any())).thenReturn(true);

        assertThrows(BookingOverlappingException.class, () -> {
            service.createBlock(request);
        });
    }

    @Test
    void validateBlockThrowsDateRangeExceptionOnCreation() {
        LocalDate startDate = LocalDate.of(2023, 1, 11);
        LocalDate endDate = LocalDate.of(2023, 1, 10);

        BlockRequest request = new BlockRequest();
        request.setPropertyId(1L);
        request.setStartDate(startDate);
        request.setEndDate(endDate);

        assertThrows(DateRangeException.class, () -> {
            service.createBlock(request);
        });
    }

    @Test
    void verifySuccessfulBlockDeletion() {
        doNothing().when(blockRepository).deleteById(any());
        service.deleteBlock(1L);
        verify(blockRepository).deleteById(any());
    }

    @Test
    void verifySuccessfulBlockUpdate() {
        LocalDate startDate = LocalDate.of(2023, 1, 10);
        LocalDate endDate = LocalDate.of(2023, 1, 15);

        LocalDate startDateUpdate = LocalDate.of(2023, 1, 12);
        LocalDate endDateUpdate = LocalDate.of(2023, 1, 18);

        BlockRequest request = new BlockRequest();
        request.setPropertyId(PROPERTY_ID);
        request.setStartDate(startDateUpdate);
        request.setEndDate(endDateUpdate);

        when(bookingRepository.hasBooking(any(), any(), any())).thenReturn(false);
        when(propertyRepository.findById(any())).thenReturn(Optional.of(buildPropertyMock(1L, buildUserMock(1L))));
        BlockEntity blockMock = buildBlockMock(1L, GUEST_ID, PROPERTY_ID, startDate, endDate);
        when(blockRepository.findById(any())).thenReturn(Optional.of(blockMock));
        when(blockRepository.save(any())).then(returnsFirstArg());

        BlockResponse response = service.updateBlock(1L, request);

        assertEquals(response.getId(), blockMock.getId());
        assertEquals(response.getStartDate(), startDateUpdate);
        assertEquals(response.getEndDate(), endDateUpdate);
    }

    @Test
    void validateBlockThrowsBookingOverlappingExceptionOnUpdate() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 10);

        BlockRequest request = new BlockRequest();
        request.setPropertyId(1L);
        request.setStartDate(startDate);
        request.setEndDate(endDate);

        when(bookingRepository.hasBooking(any(), any(), any())).thenReturn(true);

        assertThrows(BookingOverlappingException.class, () -> {
            service.createBlock(request);
        });
    }
    @Test
    void validateBlockThrowNoSuchElementExceptionOnUpdate() {
        LocalDate startDateUpdate = LocalDate.of(2023, 1, 1);
        LocalDate endDateUpdate = LocalDate.of(2023, 1, 10);

        BlockRequest request = new BlockRequest();
        request.setPropertyId(1L);
        request.setStartDate(startDateUpdate);
        request.setEndDate(endDateUpdate);

        when(propertyRepository.findById(1L)).thenReturn(Optional.of(buildPropertyMock(1L, buildUserMock(1L))));
        when(blockRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            service.updateBlock(1L, request);
        });
    }
}