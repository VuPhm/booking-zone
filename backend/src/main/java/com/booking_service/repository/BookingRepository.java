package com.booking_service.repository;

import com.booking_service.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {}