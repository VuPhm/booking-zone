package com.booking_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "payments")
@Getter
@Setter
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @Column(name = "vnp_txn_ref", unique = true)
    private String vnpTxnRef;

    @Column(nullable = false)
    private String status;
}