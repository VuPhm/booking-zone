package com.booking.component;

import com.booking.domain.entity.ServiceItem;
import com.booking.domain.repository.ServiceItemRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@Builder
public class DataSeeder implements CommandLineRunner {

    private final ServiceItemRepository serviceItemRepository;
    // Sau này làm Mốc 2, bạn chỉ cần inject thêm repository vào đây:
    // private final TimeSlotRepository timeSlotRepository;

    @Override
    public void run(String... args) throws Exception {
        seedServiceItems();
        // seedTimeSlots(); // Sẽ viết khi làm tiếp Mốc 2
    }

    private void seedServiceItems() {
        // Kiểm tra nếu database đã có dữ liệu thì bỏ qua, tránh trùng lặp dữ liệu
        if (serviceItemRepository.count() == 0) {
            log.info(">>>> Bắt đầu khởi tạo dữ liệu mẫu cho ServiceItem...");

            ServiceItem service1 = ServiceItem.builder()
                    .name("Cắt Tóc & Tạo Kiểu Căn Bản")
                    .description("Bao gồm gội đầu xả mát, tư vấn dáng tóc phù hợp với khuôn mặt và tạo kiểu chuẩn đẹp trai.")
                    .price(BigDecimal.valueOf(150000.0))
                    .duration(45)
                    .imageUrl("https://res.cloudinary.com/bookingzone/image/upload/v1/services/hair-cut.jpg")
                    .build();

            ServiceItem service2 = ServiceItem.builder()
                    .name("Combo Chăm Sóc Da Mặt Toàn Diện")
                    .description("Tẩy tế bào chết, hút mụn cám, massage bấm huyệt mặt độc quyền BookingZone và đắp mặt nạ dưỡng chất.")
                    .price(BigDecimal.valueOf(350000.0))
                    .duration(60)
                    .imageUrl("https://res.cloudinary.com/bookingzone/image/upload/v1/services/skin-care.jpg")
                    .build();

            ServiceItem service3 = ServiceItem.builder()
                    .name("Gói Combo VIP BookingZone Đặc Biệt")
                    .description("Trải nghiệm trọn gói bao gồm tất cả dịch vụ cao cấp: Cắt tóc tạo kiểu, phục hồi chuyên sâu, skincare và tặng kèm nước uống.")
                    .price(BigDecimal.valueOf(600000.0))
                    .duration(120)
                    .imageUrl("https://res.cloudinary.com/bookingzone/image/upload/v1/services/vip-combo.jpg")
                    .build();

            // Lưu toàn bộ vào PostgreSQL
            serviceItemRepository.saveAll(List.of(service1, service2, service3));
            log.info(">>>> Đã tự động sinh thành công {} dịch vụ mẫu!", serviceItemRepository.count());
        } else {
            log.info(">>>> Dữ liệu ServiceItem đã tồn tại, bỏ qua bước sinh dữ liệu mẫu.");
        }
    }
}