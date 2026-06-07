package com.booking.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "provider")
@Getter
@Setter
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String address;

    private String phone;

    private Boolean isActive = true;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User owner;

    @OneToMany(
            mappedBy = "provider",
            cascade = CascadeType.ALL,
            orphanRemoval = false
    )
    private List<ServiceItem> services = new ArrayList<>();
}