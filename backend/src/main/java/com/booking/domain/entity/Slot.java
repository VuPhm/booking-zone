package com.booking.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "slots")
@Getter @Setter
public class Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate slotDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private String status;

    @OneToMany(mappedBy = "slot")
    private List<Booking> bookings;

    public Slot() {
    }

}