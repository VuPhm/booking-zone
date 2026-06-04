package com.booking.service;

import com.booking.domain.entity.TimeSlot;

import java.util.List;

public interface TimeSlotService {
    List<TimeSlot> getAvailableSlots();
    List<TimeSlot> getAllSlots();
    TimeSlot createSlot(TimeSlot timeSlot);
    void deleteSlot(Long id);
}