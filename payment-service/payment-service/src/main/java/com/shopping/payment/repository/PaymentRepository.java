package com.shopping.payment.repository;

import com.shopping.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByOrderId(Long orderId);

    List<Payment> findByPaymentStatus(String paymentStatus);

    Payment findByTransactionId(String transactionId);
}