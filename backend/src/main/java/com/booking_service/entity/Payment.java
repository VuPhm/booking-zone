package com.booking_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // Khóa ngoại trỏ ngược lại duy nhất một Booking ID
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false, unique = true)
    private Booking booking;

    // Mã tham chiếu giao dịch gửi sang VNPay (vnp_TxnRef). Bắt buộc phải là DUY NHẤT (Unique)
    @Column(name = "vnp_txn_ref", nullable = false, unique = true, length = 100)
    private String vnpTxnRef;

    // Trạng thái hóa đơn: PENDING, SUCCESS, FAILED
    @Column(nullable = false, length = 30)
    private String status = "PENDING";
}