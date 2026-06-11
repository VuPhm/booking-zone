package com.booking.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String fullName;
    private String email;
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // Giữ 1 role duy nhất theo yêu cầu

    // --- Các trường mở rộng cho Provider nằm chung tại đây nếu ít dữ liệu ---
    private String providerBusinessName; // Tên tiệm / Tên dịch vụ hiển thị (Chỉ có nếu là PROVIDER)
    private String bio;                  // Giới thiệu ngắn về tay nghề
}