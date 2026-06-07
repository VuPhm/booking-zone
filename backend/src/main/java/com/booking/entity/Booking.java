package com.booking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Getter @Setter
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;

    private String customerEmail;

    private LocalDateTime bookedAt;

    @ManyToOne
    @JoinColumn(name = "slot_id", nullable = false)
    private Slot slot;

    public Booking() {
    }

    public Booking(Long id,
                   String customerName,
                   String customerEmail,
                   LocalDateTime bookedAt,
                   Slot slot) {
        this.id = id;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.bookedAt = bookedAt;
        this.slot = slot;
    }
}