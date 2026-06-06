package com.shopping.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopping.product.entity.Product;
import com.shopping.product.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
    ProductRepository repository;
	
	public Product addProduct(Product product)
	{
		return repository.save(product);
	}
	
	public List<Product> getAllProducts()
	{
		return repository.findAll();
		
	}
	
	public Product getProductById(Long id)
	{
		return repository.findById(id).orElseThrow(()->new RuntimeException("Product not found"));
		
	}
	
	public void deleteProduct(Long id)
	{
		repository.deleteById(id);
		
	}

}
