package com.booking_service.controller;

import com.booking_service.entity.Service;
import com.booking_service.entity.Slot;
import com.booking_service.exception.ResourceNotFoundException;
import com.booking_service.repository.ServiceRepository;
import com.booking_service.repository.SlotRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.ResponseEntity.badRequest;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final ServiceRepository serviceRepository;
    private final SlotRepository slotRepository;

    @PostMapping("/services")
    public ResponseEntity<?> createService(@Valid @RequestBody Service service) {
        if (serviceRepository.existsByName(service.getName())) {
            return badRequest().body("Error: Tên dịch vụ đã tồn tại.");
        }
        Service savedService = serviceRepository.save(service);
        return ResponseEntity.ok(savedService);
    }

    @PostMapping("/services/{serviceId}/slots")
    public ResponseEntity<?> createSlot(@PathVariable UUID serviceId, @Valid @RequestBody Slot slot) {
        return serviceRepository.findById(serviceId)
                .map(service -> {
                    // Thiết lập mối quan hệ song phương để Hibernate nhận diện khóa ngoại chính xác
                    slot.setService(service);
                    slot.setCurrentBooked(0);

                    Slot savedSlot = slotRepository.save(slot);
                    return ResponseEntity.ok(savedSlot);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dịch vụ", "SERVICE_NOT_FOUND"));
    }
}