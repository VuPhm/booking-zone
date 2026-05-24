package com.booking_service.security;

import com.booking_service.security.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private final long JWT_EXPIRATION = 86400000L; // 24 giờ
    private final long REFRESH_EXPIRATION = 604800000L; // 7 ngày

    // JJWT 0.12.x yêu cầu đối tượng SecretKey cụ thể
    private javax.crypto.SecretKey getSigningKey() {
        byte[] keyBytes = this.jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
                .subject(userPrincipal.getEmail()) // 0.12.x dùng .subject() thay cho .setSubject()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(getSigningKey()) // Không cần truyền tham số thuật toán, JJWT tự nhận diện độ dài key
                .compact();
    }

    public String generateRefreshToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
                .subject(userPrincipal.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION))
                .signWith(getSigningKey())
                .compact();
    }

    public String generateAccessTokenFromRefresh(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(getSigningKey())
                .compact();
    }

    public String getEmailFromToken(String token) {
        return Jwts.parser() // Bản 0.12.x dùng thẳng .parser() quay lại nhưng trả về Builder mới
                .verifyWith(getSigningKey()) // Thay thế cho .setSigningKey() cũ
                .build()
                .parseSignedClaims(token) // Thay thế cho .parseClaimsJws() cũ
                .getPayload() // Thay thế cho .getBody() cũ
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}