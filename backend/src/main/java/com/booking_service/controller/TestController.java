package com.booking_service.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@Profile("test")
@RestController
@RequestMapping("/api/services") // Khớp hoàn toàn với endpoint trong kịch bản test .http
public class TestController {

    @GetMapping
    public ResponseEntity<?> getMockServices() {
        // Trả về dữ liệu giả lập dạng JSON để kiểm tra luồng Auth thành công
        List<Map<String, Object>> mockServices = List.of(
            Map.of("id", "11111111-2222-3333-4444-555555555555", "name", "Gói Chăm Sóc Da Basic", "price", 150000),
            Map.of("id", "66666666-7777-8888-9999-000000000000", "name", "Cắt Tóc Nam Cấp Tốc", "price", 80000)
        );
        return ResponseEntity.ok(mockServices);
    }
}