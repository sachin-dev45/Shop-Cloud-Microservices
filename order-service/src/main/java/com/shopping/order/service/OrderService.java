package com.shopping.order.service;

import com.shopping.order.dto.ProductDTO;
import com.shopping.order.entity.Order;
import com.shopping.order.feign.ProductClient;
import com.shopping.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository repository;

  
    @Autowired
    ProductClient productClient;
 
    public Order placeOrder(Order order) {
       
        ProductDTO product = productClient.getProductById(
                                order.getProductId()
                             );

       
        if (product == null) {
            throw new RuntimeException(
                "Product not found with ID: " + order.getProductId()
            );
        }

      
        if (product.getQuantity() < order.getQuantity()) {
            throw new RuntimeException(
                "Not enough stock! " +
                "Available: " + product.getQuantity() +
                ", Requested: " + order.getQuantity()
            );
        }

     
        order.setProductName(product.getName());
        order.setTotalPrice(product.getPrice() * order.getQuantity());
        order.setStatus("PLACED");

        return repository.save(order);
    }

   
    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    
    public Order getOrderById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                    "Order not found with ID: " + id
                ));
    }

    
    public Order cancelOrder(Long id) {
        Order order = getOrderById(id);

        if (order.getStatus().equals("DELIVERED")) {
            throw new RuntimeException(
                "Cannot cancel! Order already delivered."
            );
        }

        order.setStatus("CANCELLED");
        return repository.save(order);
    }
    
    public void deleteOrder(Long id) {
        Order order = getOrderById(id);
        repository.delete(order);
    }
}