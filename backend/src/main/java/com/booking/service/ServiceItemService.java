package com.booking.service;

import com.booking.entity.ServiceItem;

import java.util.List;

public interface ServiceItemService {
    List<ServiceItem> getAllServices();
    List<ServiceItem> getServicesByCategoryId(Long categoryId);
    ServiceItem getServiceById(Long id);
    ServiceItem createService(Long categoryId, ServiceItem serviceItem);
    ServiceItem updateService(Long id, Long categoryId, ServiceItem serviceItem);
    void deleteService(Long id);
}