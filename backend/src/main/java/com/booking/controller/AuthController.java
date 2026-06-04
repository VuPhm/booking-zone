package com.booking.controller;

import com.booking.config.JwtUtils;
import com.booking.domain.entity.Role;
import com.booking.domain.entity.User;
import com.booking.domain.repository.UserRepository;
import com.booking.dto.request.LoginRequest;
import com.booking.dto.request.RegisterRequest;
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

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email này đã được sử dụng trên hệ thống");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setRole(Role.CUSTOMER);

        userRepository.save(user);
        return ResponseEntity.ok(Map.of("message", "Đăng ký tài khoản thành công"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Email hoặc mật khẩu không chính xác"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Email hoặc mật khẩu không chính xác");
        }

        String jwt = jwtUtils.generateToken(user.getEmail(), user.getRole().name());

        return ResponseEntity.ok(Map.of(
            "accessToken", jwt,
            "role", user.getRole().name(),
            "fullName", user.getFullName()
        ));
    }

    /**
     * BỔ SUNG 1: LẤY THÔNG TIN USER HIỆN TẠI (PROFILE)
     * Endpoint này bắt buộc phải đi qua Bộ lọc JwtFilter để bóc tách token
     */
    @GetMapping("/me")
    public ResponseEntity<?> getProfile() {
        // Lấy thông tin email đã được cấu hình vào SecurityContextHolder từ JwtFilter
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentLogonEmail = (String) authentication.getPrincipal();

        User user = userRepository.findByEmail(currentLogonEmail)
                .orElseThrow(() -> new BadCredentialsException("Không tìm thấy thông tin phiên đăng nhập"));

        return ResponseEntity.ok(Map.of(
            "id", user.getId(),
            "email", user.getEmail(),
            "fullName", user.getFullName(),
            "phone", user.getPhone() != null ? user.getPhone() : "",
            "role", user.getRole().name()
        ));
    }

    /**
     * BỔ SUNG 2: ĐĂNG XUẤT PHÍA BACKEND
     * Trả về thông điệp xóa session sạch sẽ, tạo tiền đề để Next.js BFF xóa httpOnly Cookie
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // Xóa thông tin bảo mật của request hiện tại trong Context hệ thống
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(Map.of("message", "Đăng xuất tài khoản thành công"));
    }
}