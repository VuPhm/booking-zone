package com.booking_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";

    // Khởi tạo Constructor tùy biến chỉ nhận chuỗi token
    public JwtResponse(String accessToken) {
        this.token = accessToken;
    }
}