package com.booking.controller;

import com.booking.entity.ServiceCategory;
import com.booking.entity.ServiceItem;
import com.booking.service.ServiceCategoryService;
import com.booking.service.ServiceItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminCatalogController {
    private final ServiceCategoryService categoryService;
    private final ServiceItemService serviceService;

    // --- QUAN LÝ CATEGORY ---
    @PostMapping("/categories")
    public ResponseEntity<ServiceCategory> createCategory(@RequestBody ServiceCategory category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(category));
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<ServiceCategory> updateCategory(@PathVariable Long id, @RequestBody ServiceCategory category) {
        return ResponseEntity.ok(categoryService.updateCategory(id, category));
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    // --- QUẢN LÝ SERVICE ITEM ---
    @PostMapping("/services")
    public ResponseEntity<ServiceItem> createService(@RequestParam Long categoryId, @RequestBody ServiceItem serviceItem) {
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceService.createService(categoryId, serviceItem));
    }

    @PutMapping("/services/{id}")
    public ResponseEntity<ServiceItem> updateService(@PathVariable Long id, @RequestParam Long categoryId, @RequestBody ServiceItem serviceItem) {
        return ResponseEntity.ok(serviceService.updateService(id, categoryId, serviceItem));
    }

    @DeleteMapping("/services/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        serviceService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}