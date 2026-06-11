package com.booking.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {

    // Khởi tạo Secret Key an toàn (Độ dài tối thiểu 256-bit cho thuật toán HS256)
    private final String JWT_SECRET = "chuoibimatnayphaithatdaivathatbaomatbookingzone2026";
    private final SecretKey key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());
    private final long JWT_EXPIRATION = 86400000L; // Token có hạn trong 1 ngày (ms)

    /**
     * PHƯƠNG PHÁP 1: Tạo Token từ Username và Role (Dạng String trực tiếp)
     * Phục vụ luồng Login xác thực thủ công hiện tại của bạn.
     */
    public String generateToken(String username, String roleName) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", roleName) // Đảm bảo đính kèm tiền tố ROLE_ cho Spring Security
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + JWT_EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * PHƯƠNG PHÁP 2: Tạo Token từ đối tượng Authentication của Spring Security
     * Giữ lại để phục vụ luồng mở rộng nâng cấp tự động (AuthenticationManager, OAuth2...) sau này.
     */
    public String generateToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        // Lấy ra role đơn duy nhất của người dùng
        String role = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("CUSTOMER");

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + JWT_EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Các hàm bổ trợ giải mã và validate giữ nguyên...
    public String getUsernameFromJwt(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}