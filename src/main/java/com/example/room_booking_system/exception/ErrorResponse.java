package com.example.room_booking_system.exception;

import java.time.Instant;

public record ErrorResponse(int status, String message, Instant timeStamp) {
}
