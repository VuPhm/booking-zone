package com.booking.controller;

import com.booking.dto.request.BookingRequest;
import com.booking.entity.Booking;
import com.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    // Giả định bạn có lớp Helper bóc tách UserId từ JWT hoặc truyền tạm thời qua Param/Header
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestHeader("X-User-Id") Long userId, @RequestBody BookingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.createBooking(userId, request));
    }

    @GetMapping("/my-history")
    public ResponseEntity<List<Booking>> getMyHistory(@RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(bookingService.getMyBookings(userId));
    }
}