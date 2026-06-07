package com.booking.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate bookingDate; // Ngày hẹn khách đặt

    // 1. Khóa ngoại liên kết bảng users (Đảm bảo thực thể User của bạn có @Id là id hoặc một trường cụ thể)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    // 2. Khóa ngoại liên kết bảng service_items (Đảm bảo thực thể ServiceItem có @Id là id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", referencedColumnName = "id", nullable = false)
    private ServiceItem service;

    // 3. Khóa ngoại liên kết bảng time_slots (Đảm bảo thực thể TimeSlot có @Id là id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "slot_id", referencedColumnName = "id", nullable = false)
    private TimeSlot slot;

    @Column(nullable = false)
    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status; // PENDING, CONFIRMED, CANCELLED
}