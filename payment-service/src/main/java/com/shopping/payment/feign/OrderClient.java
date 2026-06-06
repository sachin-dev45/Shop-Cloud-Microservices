package com.shopping.payment.feign;

import com.shopping.payment.dto.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "order-service")
public interface OrderClient {
   
    @GetMapping("/orders/{id}")
    OrderDTO getOrderById(@PathVariable("id") Long id);

    @PutMapping("/orders/{id}/cancel")
    OrderDTO cancelOrder(@PathVariable("id") Long id);
}