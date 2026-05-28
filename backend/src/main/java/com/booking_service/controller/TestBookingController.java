package com.booking_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
public class TestBookingController {

    // API này chỉ yêu cầu User ĐÃ ĐĂNG NHẬP (Bất kể quyền CUSTOMER hay ADMIN)
    @GetMapping
    public ResponseEntity<?> getMockBookings() {
        List<Map<String, Object>> mockBookings = List.of(
            Map.of("id", "b1-uuid-placeholder", "status", "CONFIRMED", "service", "Rửa xe máy"),
            Map.of("id", "b2-uuid-placeholder", "status", "PENDING", "service", "Sửa điều hòa")
        );
        return ResponseEntity.ok(mockBookings);
    }

    // API nâng cao: Chỉ cho phép tài khoản có Role là ADMIN truy cập (Dùng để test phân quyền nâng cao nếu cần)
    @GetMapping("/admin-only")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAdminStats() {
        return ResponseEntity.ok(Map.of("totalRevenue", 1500000, "totalBookings", 42));
    }
}