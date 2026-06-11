package com.booking.controller;

import com.booking.entity.Role;
import com.booking.entity.User;
import com.booking.repository.UserRepository;
import com.booking.dto.request.LoginRequest;
import com.booking.dto.request.RegisterRequest;
import com.booking.dto.response.ApiResponse;
import com.booking.dto.response.LoginResponse;
import com.booking.dto.response.UserProfileResponse;
import com.booking.security.JwtProvider;
import com.booking.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(
            @Valid @RequestBody RegisterRequest request) {

        authService.register(request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Đăng ký tài khoản thành công",
                        null
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Đăng nhập thành công",
                        authService.login(request)
                )
        );
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getProfile() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Lấy thông tin người dùng thành công",
                        authService.getProfile()
                )
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout() {

        authService.logout();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Đăng xuất thành công",
                        null
                )
        );
    }
}