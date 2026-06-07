package com.booking.service;

import com.booking.dto.request.BookingRequest;
import com.booking.entity.Booking;

import java.util.List;

public interface BookingService {
    Booking createBooking(Long userId, BookingRequest request);
    List<Booking> getMyBookings(Long userId);
}