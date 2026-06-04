package com.booking.controller;

import com.booking.domain.entity.TimeSlot;
import com.booking.service.TimeSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/slots")
@RequiredArgsConstructor
public class AdminTimeSlotController {

    private final TimeSlotService timeSlotService;

    @GetMapping
    public ResponseEntity<?> getAllSlots() {
        return ResponseEntity.ok(timeSlotService.getAllSlots());
    }

    @PostMapping
    public ResponseEntity<?> createSlot(@RequestBody TimeSlot timeSlot) {
        return ResponseEntity.ok(timeSlotService.createSlot(timeSlot));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSlot(@PathVariable Long id) {
        timeSlotService.deleteSlot(id);
        return ResponseEntity.ok().body("{\"message\": \"Xóa khung giờ thành công\"}");
    }
}