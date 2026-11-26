package com.example.room_booking_system.exception;

import java.time.LocalDateTime;

public record ErrorResponse(int status, String message, LocalDateTime timeStamp) {
}
