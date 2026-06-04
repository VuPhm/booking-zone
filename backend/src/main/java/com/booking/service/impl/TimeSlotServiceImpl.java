package com.booking.service.impl;

import com.booking.domain.entity.TimeSlot;
import com.booking.domain.repository.TimeSlotRepository;
import com.booking.service.TimeSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeSlotServiceImpl implements TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;

    @Override
    public List<TimeSlot> getAvailableSlots() {
        return timeSlotRepository.findByStatus("AVAILABLE");
    }

    @Override
    public List<TimeSlot> getAllSlots() {
        return timeSlotRepository.findAll();
    }

    @Override
    public TimeSlot createSlot(TimeSlot timeSlot) {
        timeSlot.setStatus("AVAILABLE"); // Đảm bảo luôn AVAILABLE khi mới tạo
        return timeSlotRepository.save(timeSlot);
    }

    @Override
    public void deleteSlot(Long id) {
        timeSlotRepository.deleteById(id);
    }
}