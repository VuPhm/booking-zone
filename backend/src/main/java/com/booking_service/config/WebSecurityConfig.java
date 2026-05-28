package com.booking_service.config;

import com.booking_service.security.AuthEntryPointJwt;
import com.booking_service.security.AuthTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    private final AuthEntryPointJwt unauthorizedHandler;

    public WebSecurityConfig(AuthEntryPointJwt unauthorizedHandler) {
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. Vô hiệu hóa CORS mặc định và CSRF thông qua Lambda
                .cors(cors -> cors.configure(http))
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Cấu hình xử lý ngoại lệ khi xác thực thất bại (Trả về 401 thay vì trang Login mặc định)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))

                // 3. Thiết lập chế độ Stateless Session
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 4. Cấu hình phân quyền chi tiết cho các Endpoint
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()         // Endpoint đăng nhập, đăng ký mở tự do
                        .requestMatchers("/api/services/public/**").permitAll() // Xem danh sách dịch vụ không cần token
                        .anyRequest().authenticated()                         // Tất cả các request khác phải check JWT
                );

        // 5. Gài bộ lọc kiểm tra JWT vào trước bộ lọc UsernamePassword của Spring
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}