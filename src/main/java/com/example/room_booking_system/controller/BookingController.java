package com.example.room_booking_system.controller;

import com.example.room_booking_system.dto.BookingRequest;
import com.example.room_booking_system.dto.BookingResponse;
import com.example.room_booking_system.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@Tag(name = "Bookings", description = "Endpoints for creating, viewing, and canceling room bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    @Operation(
            summary = "Create a booking",
            description = "Creates a new room booking after validating the request payload and availability."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Booking created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookingResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid request payload or validation failure", content = @Content),
            @ApiResponse(responseCode = "409", description = "Room is already booked for the requested time slot", content = @Content)
    })
    public ResponseEntity<BookingResponse> createBooking(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Details of the room booking to create",
                    required = true,
                    content = @Content(schema = @Schema(implementation = BookingRequest.class))
            )
            @Valid @RequestBody BookingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookingService.createBooking(request));
    }

    @GetMapping("/my")
    @Operation(
            summary = "Get user bookings",
            description = "Returns all bookings associated with the specified user ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Bookings retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookingResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Missing or invalid userId parameter", content = @Content)
    })
    public List<BookingResponse> getMyBookings(
            @Parameter(description = "ID of the user whose bookings are requested", required = true, example = "1")
            @RequestParam Long userId) {
        return bookingService.getBookingsForUser(userId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Cancel a booking",
            description = "Cancels an existing booking if it belongs to the specified user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Booking canceled successfully", content = @Content),
            @ApiResponse(responseCode = "403", description = "User is not authorized to cancel this booking", content = @Content),
            @ApiResponse(responseCode = "404", description = "Booking not found", content = @Content)
    })
    public void cancelBooking(
            @Parameter(description = "ID of the booking to cancel", required = true, example = "10")
            @PathVariable Long id,
            @Parameter(description = "ID of the user requesting the cancellation", required = true, example = "1")
            @RequestParam Long userId) {
        bookingService.cancelBooking(id, userId);
    }
}