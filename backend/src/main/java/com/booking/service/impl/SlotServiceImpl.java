package com.booking.service.impl;

import com.booking.domain.entity.Slot;
import com.booking.domain.repository.SlotRepository;
import com.booking.service.SlotService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SlotServiceImpl implements SlotService {

    private final SlotRepository slotRepository;

    public SlotServiceImpl(SlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }

    @Override
    public Slot createSlot(Slot slot) {
        return slotRepository.save(slot);
    }

    @Override
    public List<Slot> getAllSlots() {
        return slotRepository.findAll();
    }

    @Override
    public Slot getSlotById(Long id) {
        return slotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Slot not found"));
    }

    @Override
    public List<Slot> getAvailableSlots() {
        return slotRepository.findByStatus("AVAILABLE");
    }

    @Override
    public List<Slot> getAvailableSlotsByDate(LocalDate date) {
        return slotRepository.findBySlotDateAndStatus(
                date,
                "AVAILABLE"
        );
    }

    @Override
    public void deleteSlot(Long id) {
        slotRepository.deleteById(id);
    }

    @Override
    public Slot updateSlot(Long id, Slot updatedSlot) {

        Slot slot = slotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        slot.setSlotDate(updatedSlot.getSlotDate());
        slot.setStartTime(updatedSlot.getStartTime());
        slot.setEndTime(updatedSlot.getEndTime());
        slot.setStatus(updatedSlot.getStatus());

        return slotRepository.save(slot);
    }
}