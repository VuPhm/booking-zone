package com.booking.service;

import com.booking.dto.request.CreateServiceRequest;
import com.booking.dto.request.UpdateServiceRequest;
import com.booking.dto.response.ServiceItemResponse;
import jakarta.validation.Valid;

import java.security.Principal;

public interface ProviderServiceItemService {



    ServiceItemResponse getById(
            Long id,
            Principal principal
    );

    ServiceItemResponse update(
            Long id,
            UpdateServiceRequest request,
            Principal principal
    );

    ServiceItemResponse create(@Valid CreateServiceRequest request, Principal principal);
}