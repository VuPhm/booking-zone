package com.booking_service.repository;

import com.booking_service.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ServiceRepository extends JpaRepository<Service, UUID> {
    boolean existsByName(String name);
}