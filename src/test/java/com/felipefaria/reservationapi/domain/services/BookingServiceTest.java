package com.felipefaria.reservationapi.domain.services;

import com.felipefaria.reservationapi.application.web.controllers.request.BookingRequest;
import com.felipefaria.reservationapi.application.web.controllers.request.BookingUpdateRequest;
import com.felipefaria.reservationapi.application.web.controllers.response.BookingResponse;
import com.felipefaria.reservationapi.application.web.exception.BlockOverlappingException;
import com.felipefaria.reservationapi.application.web.exception.BookingOverlappingException;
import com.felipefaria.reservationapi.application.web.exception.DateRangeException;
import com.felipefaria.reservationapi.application.web.exception.RebookingException;
import com.felipefaria.reservationapi.domain.entities.BookingStatus;
import com.felipefaria.reservationapi.domain.gateways.BlockGateway;
import com.felipefaria.reservationapi.domain.gateways.BookingGateway;
import com.felipefaria.reservationapi.domain.gateways.PropertyGateway;
import com.felipefaria.reservationapi.domain.gateways.UserGateway;
import com.felipefaria.reservationapi.resource.repository.jpa.BlockRepository;
import com.felipefaria.reservationapi.resource.repository.jpa.BookingRepository;
import com.felipefaria.reservationapi.resource.repository.jpa.PropertyRepository;
import com.felipefaria.reservationapi.resource.repository.jpa.UserRepository;
import com.felipefaria.reservationapi.resource.repository.jpa.entities.BookingEntity;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class BookingServiceTest extends AbstractTestBase {


    @MockBean
    BookingRepository bookingRepository;
    @MockBean
    BlockRepository blockRepository;
    @MockBean
    PropertyRepository propertyRepository;
    @MockBean
    UserRepository userRepository;

    @SpyBean
    BlockGateway blockGateway;
    @SpyBean
    BookingGateway bookingGateway;
    @SpyBean
    PropertyGateway propertyGateway;
    @SpyBean
    UserGateway userGateway;

    BookingService service;

    @BeforeEach
    void setUp() {
        service = new BookingService(bookingGateway, blockGateway, propertyGateway, userGateway);
    }

    @Test
    void verifySuccessfulBookingCreation() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 10);

        BookingRequest request = new BookingRequest();
        request.setPropertyId(PROPERTY_ID);
        request.setGuestId(GUEST_ID);
        request.setStartDate(startDate);
        request.setEndDate(endDate);

        when(blockRepository.hasBlock(PROPERTY_ID, startDate, endDate)).thenReturn(false);
        when(bookingRepository.hasBooking(any(), any(), any())).thenReturn(false);

        BookingEntity bookingMock = buildBookingMock(1L, OWNER_ID, GUEST_ID, PROPERTY_ID, startDate, endDate, BookingStatus.BOOKED.name());
        when(propertyRepository.findById(PROPERTY_ID)).thenReturn(Optional.of(bookingMock.getProperty()));
        when(userRepository.findById(GUEST_ID)).thenReturn(Optional.of(bookingMock.getGuest()));
        when(bookingRepository.save(any())).thenReturn(bookingMock);

        BookingResponse response = service.createBooking(request);

        assertEquals(response.getId(), bookingMock.getId());
        assertEquals(response.getStartDate(), startDate);
        assertEquals(response.getEndDate(), endDate);
        assertEquals(response.getProperty().getId(), PROPERTY_ID);
        assertEquals(response.getProperty().getOwner().getId(), OWNER_ID);
        assertEquals(response.getGuest().getId(), GUEST_ID);
        assertEquals(response.getStatus(), "BOOKED");
    }

    //validateBookingThrowsDateRangeExceptionOnUpdate
    //validateBookingThrowsBookingOverlappingExceptionOnUpdate
    //validateBookingThrowsBlockOverlappingExceptionOnUpdate
    @Test
    void validateBookingThrowsDateRangeExceptionOnCreation() {
        LocalDate startDate = LocalDate.of(2023, 1, 10);
        LocalDate endDate = LocalDate.of(2023, 1, 1);

        BookingRequest request = new BookingRequest();
        request.setPropertyId(PROPERTY_ID);
        request.setGuestId(GUEST_ID);
        request.setStartDate(startDate);
        request.setEndDate(endDate);

        when(blockRepository.hasBlock(PROPERTY_ID, startDate, endDate)).thenReturn(false);
        when(bookingRepository.hasBooking(any(), any(), any())).thenReturn(false);

        BookingEntity bookingMock = buildBookingMock(1L, OWNER_ID, GUEST_ID, PROPERTY_ID, startDate, endDate, BookingStatus.BOOKED.name());
        when(propertyRepository.findById(PROPERTY_ID)).thenReturn(Optional.of(bookingMock.getProperty()));
        when(userRepository.findById(GUEST_ID)).thenReturn(Optional.of(bookingMock.getGuest()));
        when(bookingRepository.save(any())).thenReturn(bookingMock);

        assertThrows(DateRangeException.class, () -> {
            service.createBooking(request);
        });
    }

    @Test
    void validateBookingThrowsBookingOverlappingExceptionOnCreation() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 10);

        BookingRequest request = new BookingRequest();
        request.setPropertyId(PROPERTY_ID);
        request.setGuestId(GUEST_ID);
        request.setStartDate(startDate);
        request.setEndDate(endDate);

        when(blockRepository.hasBlock(PROPERTY_ID, startDate, endDate)).thenReturn(false);
        when(bookingRepository.hasBooking(any(), any(), any())).thenReturn(true);

        BookingEntity bookingMock = buildBookingMock(1L, OWNER_ID, GUEST_ID, PROPERTY_ID, startDate, endDate, BookingStatus.BOOKED.name());
        when(propertyRepository.findById(PROPERTY_ID)).thenReturn(Optional.of(bookingMock.getProperty()));
        when(userRepository.findById(GUEST_ID)).thenReturn(Optional.of(bookingMock.getGuest()));
        when(bookingRepository.save(any())).thenReturn(bookingMock);

        assertThrows(BookingOverlappingException.class, () -> {
            service.createBooking(request);
        });
    }

    @Test
    void validateBookingThrowsBlockOverlappingExceptionOnCreation() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 10);

        BookingRequest request = new BookingRequest();
        request.setPropertyId(PROPERTY_ID);
        request.setGuestId(GUEST_ID);
        request.setStartDate(startDate);
        request.setEndDate(endDate);

        when(blockRepository.hasBlock(PROPERTY_ID, startDate, endDate)).thenReturn(true);
        when(bookingRepository.hasBooking(any(), any(), any())).thenReturn(false);

        BookingEntity bookingMock = buildBookingMock(1L, OWNER_ID, GUEST_ID, PROPERTY_ID, startDate, endDate, BookingStatus.BOOKED.name());
        when(propertyRepository.findById(PROPERTY_ID)).thenReturn(Optional.of(bookingMock.getProperty()));
        when(userRepository.findById(GUEST_ID)).thenReturn(Optional.of(bookingMock.getGuest()));
        when(bookingRepository.save(any())).thenReturn(bookingMock);

        assertThrows(BlockOverlappingException.class, () -> {
            service.createBooking(request);
        });
    }

    @Test
    void verifySuccessfulBookingUpdate() {
        LocalDate startDateUpdate = LocalDate.of(2023, 1, 5);
        LocalDate endDateUpdate = LocalDate.of(2023, 1, 15);

        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 10);

        BookingUpdateRequest request = new BookingUpdateRequest();
        request.setStartDate(startDateUpdate);
        request.setEndDate(endDateUpdate);

        BookingEntity bookingMock = buildBookingMock(1L, OWNER_ID, GUEST_ID, PROPERTY_ID, startDate, endDate, BookingStatus.BOOKED.name());
        when(bookingRepository.findById(any())).thenReturn(Optional.of(bookingMock));
        when(blockRepository.hasBlock(PROPERTY_ID, startDateUpdate, endDateUpdate)).thenReturn(false);
        when(bookingRepository.hasBooking(PROPERTY_ID, startDateUpdate, endDateUpdate)).thenReturn(false);
        when(bookingRepository.save(any())).then(returnsFirstArg());

        BookingResponse response = service.updateBooking(1L, request);

        assertEquals(response.getId(), bookingMock.getId());
        assertEquals(response.getStartDate(), startDateUpdate);
        assertEquals(response.getEndDate(), endDateUpdate);
        assertEquals(response.getStatus(), "BOOKED");
    }

    @Test
    void validateBookingThrowsDateRangeExceptionOnUpdate() {
        LocalDate startDateUpdate = LocalDate.of(2023, 1, 15);
        LocalDate endDateUpdate = LocalDate.of(2023, 1, 5);

        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 10);

        BookingUpdateRequest request = new BookingUpdateRequest();
        request.setStartDate(startDateUpdate);
        request.setEndDate(endDateUpdate);

        BookingEntity bookingMock = buildBookingMock(1L, OWNER_ID, GUEST_ID, PROPERTY_ID, startDate, endDate, BookingStatus.BOOKED.name());
        when(bookingRepository.findById(any())).thenReturn(Optional.of(bookingMock));
        when(blockRepository.hasBlock(PROPERTY_ID, startDateUpdate, endDateUpdate)).thenReturn(false);
        when(bookingRepository.hasBooking(PROPERTY_ID, startDateUpdate, endDateUpdate)).thenReturn(false);
        when(bookingRepository.save(any())).then(returnsFirstArg());

        assertThrows(DateRangeException.class, () -> {
            service.updateBooking(1L, request);
        });
    }

    @Test
    void validateBookingThrowsBookingOverlappingExceptionOnUpdate() {
        LocalDate startDateUpdate = LocalDate.of(2023, 1, 5);
        LocalDate endDateUpdate = LocalDate.of(2023, 1, 15);

        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 10);

        BookingUpdateRequest request = new BookingUpdateRequest();
        request.setStartDate(startDateUpdate);
        request.setEndDate(endDateUpdate);

        BookingEntity bookingMock = buildBookingMock(1L, OWNER_ID, GUEST_ID, PROPERTY_ID, startDate, endDate, BookingStatus.BOOKED.name());
        when(bookingRepository.findById(any())).thenReturn(Optional.of(bookingMock));
        when(blockRepository.hasBlock(PROPERTY_ID, startDateUpdate, endDateUpdate)).thenReturn(false);
        when(bookingRepository.hasBooking(1L, PROPERTY_ID, startDateUpdate, endDateUpdate)).thenReturn(true);
        when(bookingRepository.save(any())).then(returnsFirstArg());

        assertThrows(BookingOverlappingException.class, () -> {
            service.updateBooking(1L, request);
        });
    }

    @Test
    void validateBookingThrowsBlockOverlappingExceptionOnUpdate() {
        LocalDate startDateUpdate = LocalDate.of(2023, 1, 5);
        LocalDate endDateUpdate = LocalDate.of(2023, 1, 15);

        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 10);

        BookingUpdateRequest request = new BookingUpdateRequest();
        request.setStartDate(startDateUpdate);
        request.setEndDate(endDateUpdate);

        BookingEntity bookingMock = buildBookingMock(1L, OWNER_ID, GUEST_ID, PROPERTY_ID, startDate, endDate, BookingStatus.BOOKED.name());
        when(bookingRepository.findById(any())).thenReturn(Optional.of(bookingMock));
        when(blockRepository.hasBlock(PROPERTY_ID, startDateUpdate, endDateUpdate)).thenReturn(true);
        when(bookingRepository.hasBooking(PROPERTY_ID, startDateUpdate, endDateUpdate)).thenReturn(false);
        when(bookingRepository.save(any())).then(returnsFirstArg());

        assertThrows(BlockOverlappingException.class, () -> {
            service.updateBooking(1L, request);
        });
    }

    @Test
    void verifySuccessfulBookingCancel() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 10);

        BookingEntity bookingMock = buildBookingMock(1L, OWNER_ID, GUEST_ID, PROPERTY_ID, startDate, endDate, BookingStatus.BOOKED.name());
        when(bookingRepository.findById(any())).thenReturn(Optional.of(bookingMock));
        when(bookingRepository.save(any())).then(returnsFirstArg());

        BookingResponse response = service.cancelBooking(1L);

        assertEquals(response.getId(), bookingMock.getId());
        assertEquals(response.getStartDate(), startDate);
        assertEquals(response.getEndDate(), endDate);
        assertEquals(response.getStatus(), "CANCELED");
    }

    @Test
    void validateBookingThrowsNoSuchElementExceptionOnCancel() {
        when(bookingRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            service.cancelBooking(1L);
        });
    }

    @Test
    void verifySuccessfulRebooking() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 10);

        BookingEntity bookingMock = buildBookingMock(1L, OWNER_ID, GUEST_ID, PROPERTY_ID, startDate, endDate, BookingStatus.BOOKED.name());
        when(bookingRepository.findByIdAndStatus(any(), any())).thenReturn(Optional.of(bookingMock));
        when(blockRepository.hasBlock(PROPERTY_ID, startDate, endDate)).thenReturn(false);
        when(bookingRepository.hasBooking(any(), any(), any())).thenReturn(false);

        when(propertyRepository.findById(PROPERTY_ID)).thenReturn(Optional.of(bookingMock.getProperty()));
        when(bookingRepository.findById(any())).thenReturn(Optional.of(bookingMock));
        when(bookingRepository.save(any())).then(returnsFirstArg());

        BookingResponse response = service.rebooking(1L);

        assertEquals(response.getId(), bookingMock.getId());
        assertEquals(response.getStartDate(), startDate);
        assertEquals(response.getEndDate(), endDate);
        assertEquals(response.getStatus(), "REBOOKED");
    }

    @Test
    void validateBookingThrowsRebookingExceptionOnRebooking() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 10);

        BookingEntity bookingMock = buildBookingMock(1L, OWNER_ID, GUEST_ID, PROPERTY_ID, startDate, endDate, BookingStatus.BOOKED.name());
        when(bookingRepository.findByIdAndStatus(any(), any())).thenReturn(Optional.empty());
        when(blockRepository.hasBlock(PROPERTY_ID, startDate, endDate)).thenReturn(false);
        when(bookingRepository.hasBooking(any(), any(), any())).thenReturn(false);

        when(propertyRepository.findById(PROPERTY_ID)).thenReturn(Optional.of(bookingMock.getProperty()));
        when(bookingRepository.findById(any())).thenReturn(Optional.of(bookingMock));
        when(bookingRepository.save(any())).then(returnsFirstArg());

        assertThrows(RebookingException.class, () -> {
            service.rebooking(1L);
        });
    }

    @Test
    void validateBookingThrowsBlockOverlappingExceptionOnRebooking() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 10);

        BookingEntity bookingMock = buildBookingMock(1L, OWNER_ID, GUEST_ID, PROPERTY_ID, startDate, endDate, BookingStatus.BOOKED.name());
        when(bookingRepository.findByIdAndStatus(any(), any())).thenReturn(Optional.of(bookingMock));
        when(blockRepository.hasBlock(PROPERTY_ID, startDate, endDate)).thenReturn(true);
        when(bookingRepository.hasBooking(any(), any(), any())).thenReturn(false);

        when(propertyRepository.findById(PROPERTY_ID)).thenReturn(Optional.of(bookingMock.getProperty()));
        when(bookingRepository.findById(any())).thenReturn(Optional.of(bookingMock));
        when(bookingRepository.save(any())).then(returnsFirstArg());

        assertThrows(BlockOverlappingException.class, () -> {
            service.rebooking(1L);
        });
    }

    @Test
    void validateBookingThrowsBookingOverlappingExceptionOnRebooking() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 10);

        BookingEntity bookingMock = buildBookingMock(1L, OWNER_ID, GUEST_ID, PROPERTY_ID, startDate, endDate, BookingStatus.BOOKED.name());
        when(bookingRepository.findByIdAndStatus(any(), any())).thenReturn(Optional.of(bookingMock));
        when(blockRepository.hasBlock(PROPERTY_ID, startDate, endDate)).thenReturn(false);
        when(bookingRepository.hasBooking(any(), any(), any(), any())).thenReturn(true);

        when(propertyRepository.findById(PROPERTY_ID)).thenReturn(Optional.of(bookingMock.getProperty()));
        when(bookingRepository.findById(any())).thenReturn(Optional.of(bookingMock));
        when(bookingRepository.save(any())).then(returnsFirstArg());

        assertThrows(BookingOverlappingException.class, () -> {
            service.rebooking(1L);
        });
    }

}