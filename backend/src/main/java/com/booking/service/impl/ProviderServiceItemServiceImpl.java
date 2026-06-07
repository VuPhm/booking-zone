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
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProviderServiceItemServiceImpl
        implements ProviderServiceItemService {

    private final SecurityUtils securityUtils;
    private final ProviderRepository providerRepository;
    private final ServiceItemRepository serviceItemRepository;

//    @Override
//    public ServiceItemResponse create(
//            CreateServiceRequest request
//    ) {
//
//        ServiceItem serviceItem = ServiceItem.builder()
//                .name(request.getName())
//                .description(request.getDescription())
//                .price(request.getPrice())
//                .duration(request.getDuration())
//                .imageUrl(request.getImageUrl())
//                .isActive(true)
//                .provider(getCurrentProvider())
//                .build();
//
//        return toResponse(
//                serviceItemRepository.save(serviceItem)
//        );
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<ServiceItemResponse> getMyServices() {
//
//        Long providerId = getCurrentProvider().getId();
//
//        return serviceItemRepository.findByProviderId(providerId)
//                .stream()
//                .map(this::toResponse)
//                .toList();
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public ServiceItemResponse getById(Long id) {
//
//        return toResponse(
//                getOwnedService(id)
//        );
//    }
//
//    @Override
//    public ServiceItemResponse update(
//            Long id,
//            UpdateServiceRequest request
//    ) {

//        ServiceItem serviceItem = getOwnedService(id);
//
//        serviceItem.setName(request.getName());
//        serviceItem.setDescription(request.getDescription());
//        serviceItem.setPrice(request.getPrice());
//        serviceItem.setDuration(request.getDuration());
//        serviceItem.setImageUrl(request.getImageUrl());
//
//        return toResponse(serviceItem);
//    }

//    @Override
//    public void activate(Long id) {
//        getOwnedService(id)
//                .setIsActive(true);
//    }

//    @Override
//    public void deactivate(Long id) {
//        getOwnedService(id)
//                .setIsActive(false);
//    }

    private Provider getCurrentProvider() {
        return securityUtils.getCurrentProvider();
    }

    private ServiceItem getOwnedService(Long id) {

        Long providerId = getCurrentProvider().getId();

        return serviceItemRepository
                .findByIdAndProviderId(id, providerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy dịch vụ với ID: " + id
                        ));
    }

    private ServiceItemResponse toResponse(
            ServiceItem serviceItem
    ) {

        return ServiceItemResponse.builder()
                .id(serviceItem.getId())
                .name(serviceItem.getName())
                .description(serviceItem.getDescription())
                .price(serviceItem.getPrice())
                .duration(serviceItem.getDuration())
                .imageUrl(serviceItem.getImageUrl())
                .isActive(serviceItem.getIsActive())
                .build();
    }

    @Override
    public ServiceItemResponse create(
            CreateServiceRequest request,
            Principal principal
    ) {

        Provider provider = providerRepository
                .findByOwnerUsername(principal.getName())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy provider"
                        ));

        ServiceItem serviceItem = ServiceItem.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .duration(request.getDuration())
                .imageUrl(request.getImageUrl())
                .isActive(true)
                .provider(provider)
                .build();

        return toResponse(
                serviceItemRepository.save(serviceItem)
        );
    }
    @Override
    @Transactional(readOnly = true)
    public List<ServiceItemResponse> getMyServices(
            Principal principal
    ) {

        Provider provider = providerRepository
                .findByOwnerUsername(principal.getName())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy provider"
                        ));

        return serviceItemRepository
                .findByProviderId(provider.getId())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private ServiceItem getOwnedService(
            Long id,
            Principal principal
    ) {

        Provider provider = getCurrentProvider();

        return serviceItemRepository
                .findByIdAndProviderId(
                        id,
                        provider.getId()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy dịch vụ với ID: " + id
                        ));
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
    public void activate(
            Long id,
            Principal principal
    ) {
        getOwnedService(id, principal)
                .setIsActive(true);
    }

    @Override
    public void deactivate(
            Long id,
            Principal principal
    ) {
        getOwnedService(id, principal)
                .setIsActive(false);
    }
}