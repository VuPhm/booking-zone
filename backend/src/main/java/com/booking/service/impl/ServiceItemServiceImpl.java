package com.booking.service.impl;

import com.booking.domain.entity.ServiceItem;
import com.booking.domain.repository.ServiceItemRepository;
import com.booking.service.ServiceItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceItemServiceImpl implements ServiceItemService {

    private final ServiceItemRepository serviceRepository;

    @Override
    public List<ServiceItem> getAllServices() {
        return serviceRepository.findAll();
    }

    @Override
    public ServiceItem getServiceById(Long id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy dịch vụ với ID: " + id));
    }

    @Override
    public ServiceItem createService(ServiceItem serviceItem) {
        return serviceRepository.save(serviceItem);
    }

    @Override
    public ServiceItem updateService(Long id, ServiceItem serviceItem) {
        ServiceItem existingService = getServiceById(id);
        
        existingService.setName(serviceItem.getName());
        existingService.setDescription(serviceItem.getDescription());
        existingService.setPrice(serviceItem.getPrice());
        existingService.setDuration(serviceItem.getDuration());
        existingService.setImageUrl(serviceItem.getImageUrl());
        
        return serviceRepository.save(existingService);
    }

    @Override
    public void deleteService(Long id) {
        ServiceItem existingService = getServiceById(id);
        serviceRepository.delete(existingService);
    }
}