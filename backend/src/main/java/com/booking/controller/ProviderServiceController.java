package com.booking.controller;

import com.booking.dto.request.CreateServiceRequest;
import com.booking.dto.request.UpdateServiceRequest;
import com.booking.dto.response.ServiceItemResponse;
import com.booking.service.ProviderServiceItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/provider/services")
@RequiredArgsConstructor
public class ProviderServiceController {

    private final ProviderServiceItemService service;

    @PostMapping
    public ResponseEntity<ServiceItemResponse> create(
            @Valid @RequestBody CreateServiceRequest request,
            Principal principal
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(request, principal));
    }

    @GetMapping
    public ResponseEntity<List<ServiceItemResponse>> getMyServices(
            Principal principal
    ) {
        return ResponseEntity.ok(
                service.getMyServices(principal)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceItemResponse> getById(
            @PathVariable Long id,
            Principal principal
    ) {
        return ResponseEntity.ok(
                service.getById(id, principal)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceItemResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateServiceRequest request,
            Principal principal
    ) {
        return ResponseEntity.ok(
                service.update(id, request, principal)
        );
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activate(
            @PathVariable Long id,
            Principal principal
    ) {
        service.activate(id, principal);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(
            @PathVariable Long id,
            Principal principal
    ) {
        service.deactivate(id, principal);
        return ResponseEntity.noContent().build();
    }
}