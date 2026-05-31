package com.booking_service.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // Loại biên: Bỏ qua filter hoàn toàn nếu là endpoint công khai
        if (path.startsWith("/api/auth/") || path.startsWith("/api/services")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Logic xử lý Token hiện tại bên dưới...
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            // Đối với endpoint bảo vệ, không có token sẽ chặn tại đây hoặc để FilterChain xử lý
            filterChain.doFilter(request, response);
            return;
        }

        // Thực hiện validate token và set SecurityContextHolder...
        filterChain.doFilter(request, response);
    }

    // Hàm phụ tách chuỗi "Bearer " khỏi Header Authorization
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}