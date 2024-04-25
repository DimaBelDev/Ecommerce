package com.artland.start.ecommercebackend.repository;

import com.artland.start.ecommercebackend.model.LocalUser;
import com.artland.start.ecommercebackend.model.WebOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WebOrderRepository extends JpaRepository<WebOrder, Long> {
    List<WebOrder> findByUser(LocalUser user);

}
