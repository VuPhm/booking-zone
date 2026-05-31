package com.booking_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // Nhiều đơn đặt lịch có thể được tạo bởi một Khách hàng
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Nhiều đơn đặt lịch có thể xếp chung vào một Khung giờ (cho đến khi đầy max_capacity)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "slot_id", nullable = false)
    private Slot slot;

    // Trạng thái đơn hàng: PENDING, CONFIRMED, CANCELLED
    // Ép kiểu String để lưu trực tiếp chữ vào DB thay vì lưu số (0, 1, 2) giúp dễ đọc dữ liệu thô
    @Column(nullable = false, length = 30)
    private String status = "PENDING"; 

    // Quan hệ 1-1 với hóa đơn thanh toán. 
    // Nếu đơn đặt lịch bị xóa, bản ghi thanh toán liên quan cũng tự động biến mất (CascadeType.ALL)
    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private Payment payment;
}