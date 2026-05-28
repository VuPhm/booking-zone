package com.booking_service.controller;

import com.booking_service.dto.JwtResponse;
import com.booking_service.dto.LoginRequest;
import com.booking_service.dto.RegisterRequest;
import com.booking_service.entity.User;
import com.booking_service.repository.UserRepository;
import com.booking_service.security.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, 
                          PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        // Thực hiện xác thực email và password thô từ client gửi lên
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // Tạo chuỗi mã hóa JWT hạn dùng 24h cho user
        String jwt = jwtUtils.generateJwtToken(authentication);

        // Trả về Json thô chứa token, client tự bóc tách để lưu LocalStorage
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        // Kiểm tra xem email trùng lặp hay chưa để tránh lỗi DB Constraint
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.badRequest().body(Map.of("message", "Error: Email đã được sử dụng!"));
        }

        // Tạo tài khoản mới, bắt buộc phải mã hóa mật khẩu qua BCrypt
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPasswordHash(encoder.encode(registerRequest.getPassword()));
        user.setRole("CUSTOMER"); // Mặc định đăng ký mới luôn là CUSTOMER

        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "Đăng ký tài khoản thành công!"));
    }
}