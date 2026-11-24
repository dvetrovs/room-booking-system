package com.example.room_booking_system.controller;

import com.example.room_booking_system.dto.RoomRequest;
import com.example.room_booking_system.dto.RoomResponse;
import com.example.room_booking_system.service.RoomService;
import jakarta.validation.Valid;
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
    public ResponseEntity<RoomResponse> createRoom(@Valid @RequestBody RoomRequest roomRequest){
        RoomResponse created = roomService.createRoom(roomRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public List<RoomResponse> getAllRooms(){
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public RoomResponse getRoomById(@PathVariable Long id){
        return roomService.getRoomById(id);
    }
}
