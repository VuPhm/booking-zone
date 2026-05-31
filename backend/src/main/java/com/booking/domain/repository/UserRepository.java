package com.booking.domain.repository;

import com.booking.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Phương thức cốt lõi giúp AuthController tìm kiếm tài khoản khi đăng nhập
    Optional<User> findByEmail(String email);
}