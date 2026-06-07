package com.booking.service.impl;

import com.booking.entity.SlotStatus;
import com.booking.entity.TimeSlot;
import com.booking.repository.TimeSlotRepository;
import com.booking.service.TimeSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeSlotServiceImpl implements TimeSlotService {
    private final TimeSlotRepository timeSlotRepository;

    @Override
    public List<TimeSlot> getAllSlots() { return timeSlotRepository.findAll(); }

    @Override
    public List<TimeSlot> getAvailableSlots() { return timeSlotRepository.findByStatus(SlotStatus.AVAILABLE); }

    @Override
    public TimeSlot createSlot(TimeSlot timeSlot) {
        if (timeSlot.getStatus() == null) {
            timeSlot.setStatus(SlotStatus.AVAILABLE);
        }
        return timeSlotRepository.save(timeSlot);
    }

    @Override
    public void deleteSlot(Long id) { timeSlotRepository.deleteById(id); }
}