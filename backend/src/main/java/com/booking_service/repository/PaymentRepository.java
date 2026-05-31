package com.booking_service.repository;

import com.booking_service.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    // Phục vụ luồng VNPay Return tìm kiếm lại hóa đơn để cập nhật trạng thái
    Optional<Payment> findByVnpTxnRef(String vnpTxnRef);
}