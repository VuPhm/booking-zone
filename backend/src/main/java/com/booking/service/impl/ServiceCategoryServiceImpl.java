package com.booking.service.impl;

import com.booking.entity.ServiceCategory;
import com.booking.repository.ServiceCategoryRepository;
import com.booking.service.ServiceCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceCategoryServiceImpl implements ServiceCategoryService {
    private final ServiceCategoryRepository categoryRepository;

    @Override
    public List<ServiceCategory> getAllCategories() { return categoryRepository.findAll(); }

    @Override
    public ServiceCategory getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    public ServiceCategory createCategory(ServiceCategory category) { return categoryRepository.save(category); }

    @Override
    public ServiceCategory updateCategory(Long id, ServiceCategory categoryDetails) {
        ServiceCategory category = getCategoryById(id);
        category.setName(categoryDetails.getName());
        category.setDescription(categoryDetails.getDescription());
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) { categoryRepository.deleteById(id); }
}