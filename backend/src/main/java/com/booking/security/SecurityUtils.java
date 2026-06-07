package com.booking.security;

import com.booking.domain.entity.Provider;
import com.booking.domain.repository.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtils {

    private final ProviderRepository providerRepository;

    public Provider getCurrentProvider() {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return providerRepository.findByOwnerUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Provider không tồn tại với username: " + username
                        ));
    }
}