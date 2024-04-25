package com.artland.start.ecommercebackend.repository;

import com.artland.start.ecommercebackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
