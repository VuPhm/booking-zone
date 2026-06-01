package com.booking.service;

import com.booking.domain.entity.ServiceItem;

import java.util.List;

public interface ServiceItemService {
    List<ServiceItem> getAllServices();
    ServiceItem getServiceById(Long id);
    ServiceItem createService(ServiceItem serviceItem);
    ServiceItem updateService(Long id, ServiceItem serviceItem);
    void deleteService(Long id);
}