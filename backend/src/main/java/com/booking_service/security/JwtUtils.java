package com.booking_service.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration.ms}")
    private int jwtExpirationMs;

    // Bản 0.12.x yêu cầu trả về đối tượng SecretKey tường minh
    private SecretKey getSigningKey() {
        byte[] keyBytes = this.jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Tạo Token - Cú pháp mới loại bỏ tiền tố "set"
    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .subject(userPrincipal.getUsername()) // Thay thế setSubject()
                .issuedAt(new Date())                // Thay thế setIssuedAt()
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs)) // Thay thế setExpiration()
                .signWith(getSigningKey())           // Tự nhận diện thuật toán HS512 qua độ dài Key
                .compact();
    }

    // Trích xuất Username - Dùng parser() thay cho parserBuilder() đã bị xóa
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey()) // Thay thế setSigningKey()
                .build()
                .parseSignedClaims(token)    // Thay thế parseClaimsJws()
                .getPayload()                // Thay thế getBody()
                .getSubject();
    }

    // Kiểm tra Token hợp lệ
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("JWT không hợp lệ: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT đã hết hạn: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT không được hỗ trợ: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims trống: {}", e.getMessage());
        }
        return false;
    }
}