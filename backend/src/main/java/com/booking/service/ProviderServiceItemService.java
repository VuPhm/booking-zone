package com.booking.service;

import com.booking.domain.entity.ServiceItem;
import com.booking.dto.request.CreateServiceRequest;
import com.booking.dto.request.UpdateServiceRequest;
import com.booking.dto.response.ServiceItemResponse;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

public interface ProviderServiceItemService {

    ServiceItemResponse create(
            CreateServiceRequest request,
            Principal principal
    );

    List<ServiceItemResponse> getMyServices(
            Principal principal
    );

    ServiceItemResponse getById(
            Long id,
            Principal principal
    );

    ServiceItemResponse update(
            Long id,
            UpdateServiceRequest request,
            Principal principal
    );

    void activate(
            Long id,
            Principal principal
    );

    void deactivate(
            Long id,
            Principal principal
    );

}