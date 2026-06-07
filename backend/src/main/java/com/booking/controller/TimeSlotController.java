package com.booking.controller;

import com.booking.entity.TimeSlot;
import com.booking.service.TimeSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TimeSlotController {
    private final TimeSlotService timeSlotService;

    // PUBLIC: Khách xem các khung giờ còn trống ngoài trang chủ
    @GetMapping("/slots/available")
    public ResponseEntity<List<TimeSlot>> getAvailableSlots() {
        return ResponseEntity.ok(timeSlotService.getAvailableSlots());
    }

    // ADMIN: Xem toàn bộ các slot (kể cả đã bị đặt)
    @GetMapping("/admin/slots")
    public ResponseEntity<List<TimeSlot>> getAllSlots() {
        return ResponseEntity.ok(timeSlotService.getAllSlots());
    }

    // ADMIN: Tạo mới một khung giờ làm việc
    @PostMapping("/admin/slots")
    public ResponseEntity<TimeSlot> createSlot(@RequestBody TimeSlot timeSlot) {
        return ResponseEntity.status(HttpStatus.CREATED).body(timeSlotService.createSlot(timeSlot));
    }

    // ADMIN: Xóa khung giờ
    @DeleteMapping("/admin/slots/{id}")
    public ResponseEntity<Void> deleteSlot(@PathVariable Long id) {
        timeSlotService.deleteSlot(id);
        return ResponseEntity.noContent().build();
    }
}