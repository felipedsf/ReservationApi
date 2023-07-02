package com.felipefaria.reservationapi.application.web.controllers;

import com.felipefaria.reservationapi.application.web.controllers.request.BlockRequest;
import com.felipefaria.reservationapi.application.web.controllers.request.BookingRequest;
import com.felipefaria.reservationapi.application.web.controllers.request.BookingUpdateRequest;
import com.felipefaria.reservationapi.domain.services.BlockService;
import com.felipefaria.reservationapi.domain.services.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor

@RestController
@RequestMapping("/api/v1")
public class ReservationController {

    private final BookingService bookingService;

    private final BlockService blockService;


    @PostMapping("/blocks")
    public ResponseEntity createBlock(@Valid @RequestBody BlockRequest blockRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(blockService.createBlock(blockRequest));
    }

    @DeleteMapping("/blocks/{blockId}")
    public ResponseEntity deleteBlock(@PathVariable Long blockId) {
        blockService.deleteBlock(blockId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/blocks/{blockId}")
    public ResponseEntity updateBlock(@PathVariable Long blockId, @Valid @RequestBody BlockRequest blockRequest) {
        return ResponseEntity.ok(blockService.updateBlock(blockId, blockRequest));
    }

    @GetMapping("/blocks/property/{propertyId}")
    public ResponseEntity listBlocks(@PathVariable Long propertyId) {
        return ResponseEntity.ok(blockService.listBlocks(propertyId));
    }

    // ****** Bookings ******
    @GetMapping("/bookings/property/{propertyId}")
    public ResponseEntity getBookings(@PathVariable Long propertyId) {
        return ResponseEntity.ok(bookingService.listBookings(propertyId));
    }

    @GetMapping("/bookings/{bookingId}")
    public ResponseEntity getBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/bookings/{bookingId}")
    public ResponseEntity cancelBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bookings")
    public ResponseEntity createBooking(@Valid @RequestBody BookingRequest request) {
        return ResponseEntity.ok().build();
    }

    @PutMapping("/bookings/{bookingId}")
    public ResponseEntity updateBooking(@PathVariable Long bookingId, @Valid @RequestBody BookingUpdateRequest request) {
        return ResponseEntity.ok().build();
    }

}
