package com.booking_service.repository;

import com.booking_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    
    // Tìm kiếm người dùng bằng Username phục vụ cho luồng Đăng nhập
    Optional<User> findByUsername(String username);

    // Kiểm tra trùng lặp tài khoản khi Đăng ký mới
    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}