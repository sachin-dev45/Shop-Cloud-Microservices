package com.shopping.order.feign;

import com.shopping.order.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;


@FeignClient(name = "product-service1")
public interface ProductClient {
   
    @GetMapping("/products/{id}")
    ProductDTO getProductById(@PathVariable("id") Long id);

    @GetMapping("/products")
    List<ProductDTO> getAllProducts();
}