package com.booking.domain.repository;

import com.booking.domain.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ProviderRepository
        extends JpaRepository<Provider, Long> {

    Optional<Provider> findByOwnerEmail(String email);

    Optional<Provider> findByOwnerUsername(String name);
}