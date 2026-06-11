package com.booking.service.impl;

import com.booking.entity.ServiceCategory;
import com.booking.entity.ServiceItem;
import com.booking.repository.ServiceCategoryRepository;
import com.booking.repository.ServiceItemRepository;
import com.booking.service.ServiceItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceItemServiceImpl implements ServiceItemService {
    private final ServiceItemRepository serviceItemRepository;
    private final ServiceCategoryRepository categoryRepository;

    @Override
    public List<ServiceItem> getAllServices() { return serviceItemRepository.findAll(); }

    @Override
    public List<ServiceItem> getServicesByCategoryId(Long categoryId) { return serviceItemRepository.findByCategoryId(categoryId); }

    @Override
    public ServiceItem getServiceById(Long id) {
        return serviceItemRepository.findById(id).orElseThrow(() -> new RuntimeException("Service item not found"));
    }

    @Override
    public ServiceItem createService(Long categoryId, ServiceItem serviceItem) {
        ServiceCategory category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        serviceItem.setCategory(category);
        return serviceItemRepository.save(serviceItem);
    }

    @Override
    public ServiceItem updateService(Long id, Long categoryId, ServiceItem serviceDetails) {
        ServiceItem serviceItem = getServiceById(id);
        ServiceCategory category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        
        serviceItem.setName(serviceDetails.getName());
        serviceItem.setDescription(serviceDetails.getDescription());
        serviceItem.setPrice(serviceDetails.getPrice());
        serviceItem.setDuration(serviceDetails.getDuration());
        serviceItem.setCategory(category);
        return serviceItemRepository.save(serviceItem);
    }

    @Override
    public void deleteService(Long id) { serviceItemRepository.deleteById(id); }
}