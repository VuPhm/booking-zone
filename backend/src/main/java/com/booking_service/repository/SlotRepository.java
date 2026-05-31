package com.booking_service.repository;

import com.booking_service.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface SlotRepository extends JpaRepository<Slot, UUID> {
    // Query Custom JPQL: Tìm các slot còn chỗ của một Service trong khoảng thời gian từ Start đến End
    @Query("SELECT s FROM Slot s WHERE s.service.id = :serviceId " +
            "AND s.startTime >= :startOfDay AND s.endTime <= :endOfDay " +
            "AND s.currentBooked < s.maxCapacity")
    List<Slot> findAvailableSlots(
            @Param("serviceId") UUID serviceId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );
}