package com.booking.controller;

import com.booking.domain.entity.Role;
import com.booking.domain.entity.User;
import com.booking.domain.repository.UserRepository;
import com.booking.dto.request.LoginRequest;
import com.booking.dto.request.RegisterRequest;
import com.booking.security.JwtProvider;
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
    private final JwtProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Tên đăng nhập này đã được sử dụng");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setRole(Role.CUSTOMER);

        userRepository.save(user);
        return ResponseEntity.ok(Map.of("message", "Đăng ký tài khoản thành công"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) { // Nhận chuẩn dữ liệu JSON [cite: 120]
        // Tìm kiếm tài khoản thông qua trường username mới đồng bộ [cite: 9]
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Tên đăng nhập hoặc mật khẩu không chính xác"));

        // Kiểm tra so khớp mật khẩu bằng PasswordEncoder
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Tên đăng nhập hoặc mật khẩu không chính xác");
        }

        // Gọi hàm generateToken đã được sửa đổi khớp 2 tham số String phía trên
        String jwt = jwtProvider.generateToken(user.getUsername(), user.getRole().name());

        // Trả về Object thông tin đồng bộ với Client Frontend
        return ResponseEntity.ok(Map.of(
                "token", jwt, // Đổi từ accessToken sang token để khớp destructuring tại giao diện UI
                "role", user.getRole().name(),
                "fullName", user.getFullName()
        ));
    }

    /**
     * LẤY THÔNG TIN USER HIỆN TẠI (PROFILE)
     * Endpoint này bắt buộc phải đi qua Bộ lọc JwtFilter để bóc tách token
     */
    @GetMapping("/me")
    public ResponseEntity<?> getProfile() {
        // Lấy thông tin email đã được cấu hình vào SecurityContextHolder từ JwtFilter
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentLogonEmail = (String) authentication.getPrincipal();

        User user = userRepository.findByUsername(currentLogonEmail)
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
     * ĐĂNG XUẤT PHÍA BACKEND
     * Trả về thông điệp xóa session sạch sẽ, tạo tiền đề để Next.js BFF xóa httpOnly Cookie
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // Xóa thông tin bảo mật của request hiện tại trong Context hệ thống
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(Map.of("message", "Đăng xuất tài khoản thành công"));
    }
}