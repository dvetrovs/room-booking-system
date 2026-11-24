package com.example.room_booking_system.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RoomRequest(
        @NotBlank(message = "Room name is required")
        @Size(max = 100, message = "Room name must be 100 characters or fewer")
        String name,

        @NotNull(message = "Capacity is required")
        @Min(value = 1, message = "Capacity must be at least 1")
        Integer capacity,

        @Size(max = 500, message = "Description must be 500 characters or fewer")
        String description
) {}