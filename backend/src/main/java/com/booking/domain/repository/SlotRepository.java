package com.booking.domain.repository;

import com.booking.domain.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SlotRepository extends JpaRepository<Slot, Long> {

    List<Slot> findByStatus(String status);

    List<Slot> findBySlotDateAndStatus(
            LocalDate slotDate,
            String status
    );
}