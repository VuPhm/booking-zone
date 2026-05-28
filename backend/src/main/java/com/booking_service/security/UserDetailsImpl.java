package com.booking_service.security;

import com.booking_service.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String email;

    @JsonIgnore
    private String password;

    // Danh sách quyền hạn (Roles) của User
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(UUID id, String email, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    // Static method để convert nhanh từ thực thể User trong DB sang UserDetailsImpl
    public static UserDetailsImpl build(User user) {
        // Thêm tiền tố "ROLE_" theo quy ước bắt buộc của Spring Security
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole());

        return new UserDetailsImpl(
                user.getId(),
                user.getEmail(),
                user.getPasswordHash(), // Trỏ đúng vào trường password_hash của ERD
                Collections.singletonList(authority)
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email; // Trong hệ thống của bạn, Email đóng vai trò là Username đăng nhập
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}