package com.example.room_booking_system.controller;

import com.example.room_booking_system.dto.RoomRequest;
import com.example.room_booking_system.dto.RoomResponse;
import com.example.room_booking_system.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@Tag(name = "Rooms", description = "Endpoints for creating and viewing bookable rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    @Operation(
            summary = "Create a room",
            description = "Creates a new room after validating the request payload."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Room created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RoomResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid request payload or validation failure", content = @Content)
    })
    public ResponseEntity<RoomResponse> createRoom(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Details of the room to create",
                    required = true,
                    content = @Content(schema = @Schema(implementation = RoomRequest.class))
            )
            @Valid @RequestBody RoomRequest roomRequest) {
        RoomResponse created = roomService.createRoom(roomRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    @Operation(
            summary = "Get all rooms",
            description = "Returns a list of all available rooms."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Rooms retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RoomResponse.class))
            )
    })
    public List<RoomResponse> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get room by ID",
            description = "Returns details for a specific room by its ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Room found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RoomResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "Room not found", content = @Content)
    })
    public RoomResponse getRoomById(
            @Parameter(description = "ID of the room to retrieve", required = true, example = "1")
            @PathVariable Long id) {
        return roomService.getRoomById(id);
    }
}