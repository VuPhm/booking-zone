package com.booking.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingRequest {
    private LocalDate bookingDate;
    private Long serviceId;
    private Long timeSlotId;
}