package com.booking.service.impl;

import com.booking.dto.request.BookingRequest;
import com.booking.entity.*;
import com.booking.repository.BookingRepository;
import com.booking.repository.ServiceItemRepository;
import com.booking.repository.TimeSlotRepository;
import com.booking.repository.UserRepository;
import com.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ServiceItemRepository serviceItemRepository;
    private final TimeSlotRepository timeSlotRepository;

    @Override
    @Transactional
    public Booking createBooking(Long userId, BookingRequest request) {
        // 1. Kiểm tra trùng lặp lịch đặt
        boolean isTaken = bookingRepository.existsByBookingDateAndSlotIdAndStatusNot(
                request.getBookingDate(), request.getTimeSlotId(), BookingStatus.CANCELLED);
        if (isTaken) {
            throw new RuntimeException("Khung giờ này đã có người ghim lịch vào ngày đã chọn!");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        ServiceItem service = serviceItemRepository.findById(request.getServiceId())
                .orElseThrow(() -> new RuntimeException("Service not found"));
        TimeSlot slot = timeSlotRepository.findById(request.getTimeSlotId())
                .orElseThrow(() -> new RuntimeException("Time slot not found"));

        // 2. Chuyển trạng thái slot cục bộ (Optional cho mô hình phẳng, đơn giản hóa vận hành)
        slot.setStatus(SlotStatus.BOOKED);
        timeSlotRepository.save(slot);

        // 3. Khởi tạo đơn đặt hàng
        Booking booking = Booking.builder()
                .bookingDate(request.getBookingDate())
                .user(user)
                .service(service)
                .slot(slot)
                .totalPrice(service.getPrice())
                .status(BookingStatus.CONFIRMED) // Đặt trực tiếp hoặc PENDING chờ VNPay
                .build();

        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getMyBookings(Long userId) {
        return bookingRepository.findByUserId(userId);
    }
}