package com.booking.domain.repository;

import com.booking.domain.entity.ServiceItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceItemRepository
        extends JpaRepository<ServiceItem, Long> {

    List<ServiceItem> findByProviderId(Long providerId);

    List<ServiceItem> findByProviderIdAndIsActiveTrue(Long providerId);

    Optional<ServiceItem>
    findByIdAndProviderId(Long id, Long providerId);
}