package com.booking.repository;

import com.booking.entity.Booking;
import com.booking.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
    
    // Kiểm tra xem khung giờ đó đã bị đặt vào ngày đó chưa (Concurrency Validation)
    boolean existsByBookingDateAndSlotIdAndStatusNot(LocalDate date, Long timeSlotId, BookingStatus status);
}