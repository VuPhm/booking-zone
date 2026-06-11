package com.booking.service;

import com.booking.entity.TimeSlot;

import java.util.List;

public interface TimeSlotService {
    List<TimeSlot> getAllSlots();
    List<TimeSlot> getAvailableSlots();
    TimeSlot createSlot(TimeSlot timeSlot);
    void deleteSlot(Long id);
}