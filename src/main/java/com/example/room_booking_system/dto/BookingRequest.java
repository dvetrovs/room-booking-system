package com.example.room_booking_system.dto;

import jakarta.validation.constraints.NotNull;
import java.time.Instant;

public record BookingRequest(
        @NotNull
        Long userId,

        @NotNull
        Long roomId,

        @NotNull
        Instant startTime,

        @NotNull
        Instant endTime
) {}
