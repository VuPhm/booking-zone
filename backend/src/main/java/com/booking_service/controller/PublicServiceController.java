package com.booking_service.controller;

import com.booking_service.entity.Service;
import com.booking_service.entity.Slot;
import com.booking_service.repository.ServiceRepository;
import com.booking_service.repository.SlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class PublicServiceController {

    private final ServiceRepository serviceRepository;
    private final SlotRepository slotRepository;

    @GetMapping
    public ResponseEntity<List<Service>> getAllServices() {
        return ResponseEntity.ok(serviceRepository.findAll());
    }

    @GetMapping("/{serviceId}/slots")
    public ResponseEntity<List<Slot>> getAvailableSlots(
            @PathVariable UUID serviceId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        var startOfDay = date.atStartOfDay();
        var endOfDay = date.atTime(LocalTime.MAX);

        List<Slot> slots = slotRepository.findAvailableSlots(serviceId, startOfDay, endOfDay);
        return ResponseEntity.ok(slots);
    }
}