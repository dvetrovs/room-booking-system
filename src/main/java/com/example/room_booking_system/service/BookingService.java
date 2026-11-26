package com.example.room_booking_system.service;

import com.example.room_booking_system.dto.BookingRequest;
import com.example.room_booking_system.dto.BookingResponse;
import com.example.room_booking_system.entity.Booking;
import com.example.room_booking_system.entity.BookingStatus;
import com.example.room_booking_system.entity.Room;
import com.example.room_booking_system.entity.User;
import com.example.room_booking_system.exception.ConflictException;
import com.example.room_booking_system.exception.InvalidBookingException;
import com.example.room_booking_system.exception.ResourceNotFoundException;
import com.example.room_booking_system.repository.BookingRepository;
import com.example.room_booking_system.repository.RoomRepository;
import com.example.room_booking_system.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    public BookingService(BookingRepository bookingRepository, UserRepository userRepository, RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @Transactional
    public BookingResponse createBooking(BookingRequest bookingRequest){
        if (!bookingRequest.endTime().isAfter(bookingRequest.startTime())) {
            throw new InvalidBookingException("endTime must be after startTime");
        }
        if (bookingRequest.startTime().isBefore(Instant.now())) {
            throw new InvalidBookingException("Cannot book in the past");
        }

        User user = userRepository.findById(bookingRequest.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + bookingRequest.userId()));

        Room room = roomRepository.findByIdForUpdate(bookingRequest.roomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id " + bookingRequest.roomId()));

        if (!room.isActive()) {
            throw new InvalidBookingException("Room is not active");
        }

        boolean overlaps = bookingRepository.existsOverlap(
                room.getId(), bookingRequest.startTime(), bookingRequest.endTime(), BookingStatus.CONFIRMED);
        if (overlaps) {
            throw new ConflictException("Room is already booked for that time slot");
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setRoom(room);
        booking.setStartTime(bookingRequest.startTime());
        booking.setEndTime(bookingRequest.endTime());
        booking.setStatus(BookingStatus.CONFIRMED);

        return BookingResponse.from(bookingRepository.save(booking));
    }

    public List<BookingResponse> getBookingsForUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id " + userId);
        }
        return bookingRepository.findByUserIdOrderByStartTimeDesc(userId).stream()
                .map(BookingResponse::from)
                .toList();
    }

    @Transactional
    public void cancelBooking(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id " + bookingId));
        if (!booking.getUser().getId().equals(userId)) {
            throw new InvalidBookingException("Booking belongs to another user");
        }
        booking.cancel();
    }
}
