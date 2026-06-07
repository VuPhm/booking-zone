package com.booking.controller;

import com.booking.domain.entity.ServiceItem;
import com.booking.service.ServiceItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/provider/services")
@RequiredArgsConstructor
public class ProviderServiceController {

    private final ServiceItemService serviceItemService;

    // Ví dụ: Endpoint xem danh sách dịch vụ nâng cao dành riêng cho Provider
    @GetMapping
    public ResponseEntity<List<ServiceItem>> getProviderServices() {
        // Tạm thời trả về toàn bộ dịch vụ, sau này có thể lọc theo ID của Provider
        return ResponseEntity.ok(serviceItemService.getAllServices());
    }
}