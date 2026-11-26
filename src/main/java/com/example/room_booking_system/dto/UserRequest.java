package com.example.room_booking_system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank(message = "User name is required")
        @Size(max = 100, message = "User name must be 100 characters or fewer")
        String name,

        @NotBlank(message = "User email is required")
        @Size(max = 255, message = "User email must be 255 characters or fewer")
        @Email(message = "Invalid email format")
        String email
) {
}
