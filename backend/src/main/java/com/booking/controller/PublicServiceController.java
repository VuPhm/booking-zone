package com.booking.controller;

import com.booking.entity.ServiceCategory;
import com.booking.entity.ServiceItem;
import com.booking.service.ServiceCategoryService;
import com.booking.service.ServiceItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PublicServiceController {
    private final ServiceItemService serviceService;
    private final ServiceCategoryService categoryService;

    // Lấy toàn bộ nhóm ngành
    @GetMapping("/categories")
    public ResponseEntity<List<ServiceCategory>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    // Xem toàn bộ dịch vụ, hoặc lọc theo categoryId phẳng từ RequestParam
    @GetMapping("/services")
    public ResponseEntity<List<ServiceItem>> getServices(@RequestParam(required = false) Long categoryId) {
        if (categoryId != null) {
            return ResponseEntity.ok(serviceService.getServicesByCategoryId(categoryId));
        }
        return ResponseEntity.ok(serviceService.getAllServices());
    }

    @GetMapping("/services/{id}")
    public ResponseEntity<ServiceItem> getServiceById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceService.getServiceById(id));
    }
}