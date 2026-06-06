package com.shopping.payment.service;

import com.shopping.payment.dto.OrderDTO;
import com.shopping.payment.entity.Payment;
import com.shopping.payment.feign.OrderClient;
import com.shopping.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository repository;

    @Autowired
    OrderClient orderClient;

    
    public Payment makePayment(Payment payment) {

        OrderDTO order = orderClient.getOrderById(payment.getOrderId());

        
        if (order == null) {
            throw new RuntimeException(
                "Order not found with ID: " + payment.getOrderId()
            );
        }

        if (order.getStatus().equals("CANCELLED")) {
            throw new RuntimeException(
                "Cannot pay for cancelled order!"
            );
        }

        if (order.getStatus().equals("PAID")) {
            throw new RuntimeException(
                "Order already paid!"
            );
        }

        payment.setProductId(order.getProductId());
        payment.setProductName(order.getProductName());
        payment.setAmount(order.getTotalPrice());
        payment.setPaymentStatus("SUCCESS");

        return repository.save(payment);
    }

    public List<Payment> getAllPayments() {
        return repository.findAll();
    }

    public Payment getPaymentById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                    "Payment not found with ID: " + id
                ));
    }

    public List<Payment> getPaymentsByOrderId(Long orderId) {
        return repository.findByOrderId(orderId);
    }

    
    public Payment refundPayment(Long id) {
        Payment payment = getPaymentById(id);

        if (payment.getPaymentStatus().equals("REFUNDED")) {
            throw new RuntimeException(
                "Payment already refunded!"
            );
        }

        if (payment.getPaymentStatus().equals("FAILED")) {
            throw new RuntimeException(
                "Cannot refund failed payment!"
            );
        }

        orderClient.cancelOrder(payment.getOrderId());

        payment.setPaymentStatus("REFUNDED");
        return repository.save(payment);
    }

    public List<Payment> getPaymentsByStatus(String status) {
        return repository.findByPaymentStatus(status);
    }
}