package com.booking.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginRequest {
    @NotBlank(message = "Username không được để trống")
    private String username; // Đảm bảo dùng chính xác từ khóa 'username' thay vì 'email'

    @NotBlank(message = "Password không được để trống")
    private String password;
}