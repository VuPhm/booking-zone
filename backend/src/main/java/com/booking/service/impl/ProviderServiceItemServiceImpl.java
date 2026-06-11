package com.booking.service.impl;

import com.booking.domain.entity.Provider;
import com.booking.domain.entity.ServiceItem;
import com.booking.domain.repository.ProviderRepository;
import com.booking.domain.repository.ServiceItemRepository;
import com.booking.dto.request.CreateServiceRequest;
import com.booking.dto.request.UpdateServiceRequest;
import com.booking.dto.response.ServiceItemResponse;
import com.booking.security.SecurityUtils;
import com.booking.service.ProviderServiceItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor
@Transactional
public class ProviderServiceItemServiceImpl
        implements ProviderServiceItemService {

    private final SecurityUtils securityUtils;
    private final ProviderRepository providerRepository;
    private final ServiceItemRepository serviceItemRepository;

//    @Override
    private Provider getCurrentProvider() {
        return securityUtils.getCurrentProvider();
    }

    private ServiceItemResponse toResponse(
            ServiceItem serviceItem
    ) {

        return ServiceItemResponse.builder()
//                .id(serviceItem.getId())
                .name(serviceItem.getName())
                .description(serviceItem.getDescription())
                .price(serviceItem.getPrice())
                .duration(serviceItem.getDuration())
                .imageUrl(serviceItem.getImageUrl())
                .isActive(serviceItem.getIsActive())
                .build();
    }

    @Override
    public ServiceItemResponse getById(Long id, Principal principal) {
        return null;
    }

    @Override
    public ServiceItemResponse update(Long id, UpdateServiceRequest request, Principal principal) {
        return null;
    }

    @Override
    public ServiceItemResponse create(CreateServiceRequest request, Principal principal) {
    return null;
    }

}