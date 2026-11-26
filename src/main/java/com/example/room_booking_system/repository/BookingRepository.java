package com.example.room_booking_system.repository;

import com.example.room_booking_system.entity.Booking;
import com.example.room_booking_system.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("""
        select count(b) > 0 from Booking b
        where b.room.id = :roomId
          and b.status = :status
          and b.startTime < :end
          and b.endTime > :start
        """)
    boolean existsOverlap(Long roomId, Instant start, Instant end, BookingStatus status);

    List<Booking> findByUserIdOrderByStartTimeDesc(Long userId);
}
