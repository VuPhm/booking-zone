package com.booking_service.controller;

import com.booking_service.dto.LoginRequest;
import com.booking_service.dto.RegisterRequest;
import com.booking_service.repository.UserRepository;
import com.booking_service.security.JwtUtils;
import com.booking_service.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            return ResponseEntity.badRequest().body("Email đã tồn tại!");
        }
        User user = new User();
        user.setEmail(req.getEmail());
        user.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        user.setRole("CUSTOMER"); // Role mặc định
        userRepository.save(user);
        return ResponseEntity.ok("Đăng ký thành công");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req, HttpServletResponse response) {
        // Spring Security tự động check pass
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        String accessToken = jwtUtils.generateAccessToken(auth);
        String refreshToken = jwtUtils.generateRefreshToken(auth);

        // Gắn Refresh Token vào HTTP-Only Cookie (Bảo mật XSS)
        Cookie cookie = new Cookie("refresh_token", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/api/auth/refresh"); // Giới hạn endpoint nhận cookie
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);

        return ResponseEntity.ok(Map.of("accessToken", accessToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@CookieValue(name = "refresh_token", required = false) String refreshToken) {
        if (refreshToken != null && jwtUtils.validateToken(refreshToken)) {
            String email = jwtUtils.getEmailFromToken(refreshToken);

            // Gọi hàm mới cập nhật trong JwtUtils
            String newAccessToken = jwtUtils.generateAccessTokenFromRefresh(email);

            return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh Token không hợp lệ hoặc đã hết hạn");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        // Ghi đè Cookie cũ bằng Cookie rỗng, expire ngay lập tức
        Cookie cookie = new Cookie("refresh_token", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/api/auth/refresh");
        cookie.setMaxAge(0); 
        response.addCookie(cookie);
        return ResponseEntity.ok("Đăng xuất thành công");
    }

}