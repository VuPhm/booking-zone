package com.booking_service.service;

import com.booking_service.entity.Service;
import com.booking_service.entity.Slot;
import com.booking_service.repository.ServiceRepository;
import com.booking_service.repository.SlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@org.springframework.stereotype.Service
public class PublicService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private SlotRepository slotRepository;

    // Lấy tất cả dịch vụ công khai
    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    // Xử lý bẻ ngày để tìm Slot còn trống
    public List<Slot> getAvailableSlots(UUID serviceId, LocalDate date) {
        // Chuyển LocalDate thành mốc bắt đầu ngày: 2026-05-28T00:00:00
        LocalDateTime startOfDay = date.atStartOfDay(); 
        // Chuyển LocalDate thành mốc kết thúc ngày: 2026-05-28T23:59:59.999999999
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX); 

        return slotRepository.findAvailableSlots(serviceId, startOfDay, endOfDay);
    }
}