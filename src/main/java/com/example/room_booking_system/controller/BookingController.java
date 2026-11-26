package com.example.room_booking_system.controller;

import com.example.room_booking_system.dto.BookingRequest;
import com.example.room_booking_system.dto.BookingResponse;
import com.example.room_booking_system.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody BookingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookingService.createBooking(request));
    }

    @GetMapping("/my")
    public List<BookingResponse> getMyBookings(@RequestParam Long userId) {
        return bookingService.getBookingsForUser(userId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelBooking(@PathVariable Long id, @RequestParam Long userId) {
        bookingService.cancelBooking(id, userId);
    }
}
