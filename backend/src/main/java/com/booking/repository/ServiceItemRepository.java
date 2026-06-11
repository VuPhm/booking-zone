package com.booking.repository;

import com.booking.entity.ServiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ServiceItemRepository extends JpaRepository<ServiceItem, Long> {
    // Tự động sinh câu lệnh SQL lọc dịch vụ theo danh mục con
    List<ServiceItem> findByCategoryId(Long categoryId);
}