package com.example.room_booking_system.dto;

import com.example.room_booking_system.entity.Room;

public record RoomResponse(
        Long id,
        String name,
        Integer capacity,
        String description,
        boolean active
) {
    public static RoomResponse from(Room room) {
        return new RoomResponse(
                room.getId(),
                room.getName(),
                room.getCapacity(),
                room.getDescription(),
                room.isActive()
        );
    }
}
