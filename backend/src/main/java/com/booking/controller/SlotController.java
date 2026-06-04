package com.booking.controller;

import com.booking.domain.entity.Slot;
import com.booking.service.SlotService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/slots")
public class SlotController {

    private final SlotService slotService;

    public SlotController(SlotService slotService) {
        this.slotService = slotService;
    }

    @PostMapping
    public Slot createSlot(@RequestBody Slot slot) {
        return slotService.createSlot(slot);
    }

    @GetMapping
    public List<Slot> getAllSlots() {
        return slotService.getAllSlots();
    }

    @GetMapping("/{id}")
    public Slot getSlotById(@PathVariable Long id) {
        return slotService.getSlotById(id);
    }

    @GetMapping("/available")
    public List<Slot> getAvailableSlots() {
        return slotService.getAvailableSlots();
    }

    @DeleteMapping("/{id}")
    public String deleteSlot(@PathVariable Long id) {
        slotService.deleteSlot(id);
        return "Slot deleted successfully";
    }

    @PutMapping("/{id}")
    public Slot updateSlot(@PathVariable Long id,
                           @RequestBody Slot slot) {
        return slotService.updateSlot(id, slot);
    }
}