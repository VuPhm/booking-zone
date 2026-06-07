package com.booking.controller;

import com.booking.domain.entity.ServiceItem;
import com.booking.service.ServiceItemService;
import com.booking.service.SlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceItemService serviceItemService;
    private final SlotService slotService;

    @GetMapping
    public ResponseEntity<List<ServiceItem>> getAllServices() {
        return ResponseEntity.ok(serviceItemService.getAllServices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceItem> getServiceById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceItemService.getServiceById(id));
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
//        // Thực hiện logic xóa dịch vụ tại đây
//         serviceItemService.deleteService(id);
//
//        return ResponseEntity.noContent().build(); // Trả về mã 204 No Content
//    }
}