package com.example.room_booking_system.dto;

import com.example.room_booking_system.entity.Booking;
import com.example.room_booking_system.entity.BookingStatus;

import java.time.Instant;

public record BookingResponse(
        Long id,
        Long userId,
        Long roomId,
        String roomName,
        Instant startTime,
        Instant endTime,
        BookingStatus status,
        Instant createdAt
) {
    public static BookingResponse from(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getUser().getId(),
                booking.getRoom().getId(),
                booking.getRoom().getName(),
                booking.getStartTime(),
                booking.getEndTime(),
                booking.getStatus(),
                booking.getCreatedAt());
    }
}
