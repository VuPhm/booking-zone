package com.booking.service;

import com.booking.domain.entity.Slot;

import java.time.LocalDate;
import java.util.List;

public interface SlotService {

    Slot createSlot(Slot slot);

    List<Slot> getAllSlots();

    Slot getSlotById(Long id);

    List<Slot> getAvailableSlots();

    List<Slot> getAvailableSlotsByDate(LocalDate date);

    void deleteSlot(Long id);

    Slot updateSlot(Long id, Slot slot);

}