package com.artland.start.ecommercebackend.service.impl;

import com.artland.start.ecommercebackend.model.LocalUser;
import com.artland.start.ecommercebackend.model.WebOrder;
import com.artland.start.ecommercebackend.repository.WebOrderRepository;
import com.artland.start.ecommercebackend.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private WebOrderRepository webOrderRepository;

    @Override
    public List<WebOrder> getOrders(LocalUser localUser) {
        return webOrderRepository.findByUser(localUser);
    }

}
