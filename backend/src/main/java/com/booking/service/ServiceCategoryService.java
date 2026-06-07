package com.booking.service;

import com.booking.entity.ServiceCategory;

import java.util.List;

public interface ServiceCategoryService {
    List<ServiceCategory> getAllCategories();
    ServiceCategory getCategoryById(Long id);
    ServiceCategory createCategory(ServiceCategory category);
    ServiceCategory updateCategory(Long id, ServiceCategory category);
    void deleteCategory(Long id);
}