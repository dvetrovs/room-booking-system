package com.example.room_booking_system.service;

import com.example.room_booking_system.dto.RoomRequest;
import com.example.room_booking_system.dto.RoomResponse;
import com.example.room_booking_system.entity.Room;
import com.example.room_booking_system.exception.ResourceNotFoundException;
import com.example.room_booking_system.exception.ConflictException;
import com.example.room_booking_system.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }

    @Transactional
    public RoomResponse createRoom(RoomRequest request) {
        if (roomRepository.existsByName(request.name())){
            throw new ConflictException("Room already exists: " + request.name());
        }
        Room room = new Room();
        room.setName(request.name());
        room.setCapacity(request.capacity());
        room.setDescription(request.description());
        room.setActive(true);
        return RoomResponse.from(roomRepository.save(room));
    }

    public List<RoomResponse> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(RoomResponse::from).toList();
    }

    public RoomResponse getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id " + id));
        return RoomResponse.from(room);
    }
}
