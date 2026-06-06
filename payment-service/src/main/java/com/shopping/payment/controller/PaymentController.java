package com.shopping.payment.controller;

import com.shopping.payment.entity.Payment;
import com.shopping.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/payments")
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {

    @Autowired
    PaymentService service;

    @PostMapping
    public Payment makePayment(@RequestBody Payment payment) {
        return service.makePayment(payment);
    }

    @GetMapping
    public List<Payment> getAllPayments() {
        return service.getAllPayments();
    }

    
    @GetMapping("/{id}")
    public Payment getPaymentById(@PathVariable Long id) {
        return service.getPaymentById(id);
    }
    
    @GetMapping("/order/{orderId}")
    public List<Payment> getPaymentsByOrderId(@PathVariable Long orderId) {
        return service.getPaymentsByOrderId(orderId);
    }

    @PutMapping("/{id}/refund")
    public Payment refundPayment(@PathVariable Long id) {
        return service.refundPayment(id);
    }

    @GetMapping("/status/{status}")
    public List<Payment> getPaymentsByStatus(@PathVariable String status) {
        return service.getPaymentsByStatus(status);
    }
}