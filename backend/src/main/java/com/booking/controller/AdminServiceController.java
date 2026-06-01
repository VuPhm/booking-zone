package com.booking.controller;

import com.booking.domain.entity.ServiceItem;
import com.booking.service.ServiceItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/services")
@RequiredArgsConstructor
public class AdminServiceController {

    private final ServiceItemService serviceItemService;

    @PostMapping
    public ResponseEntity<ServiceItem> createService(@RequestBody ServiceItem serviceItem) {
        return ResponseEntity.ok(serviceItemService.createService(serviceItem));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceItem> updateService(@PathVariable Long id, @RequestBody ServiceItem serviceItem) {
        return ResponseEntity.ok(serviceItemService.updateService(id, serviceItem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteService(@PathVariable Long id) {
        serviceItemService.deleteService(id);
        return ResponseEntity.ok(Map.of("message", "Xóa dịch vụ thành công"));
    }
}