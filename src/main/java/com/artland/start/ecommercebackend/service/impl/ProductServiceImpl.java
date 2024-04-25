package com.artland.start.ecommercebackend.service.impl;

import com.artland.start.ecommercebackend.model.Product;
import com.artland.start.ecommercebackend.repository.ProductRepository;
import com.artland.start.ecommercebackend.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }
}
