package com.example.room_booking_system.controller;

import com.example.room_booking_system.entity.Room;
import com.example.room_booking_system.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }
    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room){
        Room created = roomService.createRoom(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public List<Room> getAllRooms(){
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public Room getRoomById(@PathVariable Long id){
        return roomService.getRoomById(id);
    }
}
