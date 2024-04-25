package com.artland.start.ecommercebackend.api.controller.order;


import com.artland.start.ecommercebackend.model.LocalUser;
import com.artland.start.ecommercebackend.model.WebOrder;
import com.artland.start.ecommercebackend.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;


    @GetMapping
    public ResponseEntity<List<WebOrder>> getOrders(@AuthenticationPrincipal LocalUser localUser) {
       List<WebOrder> orders = orderService.getOrders(localUser);
       return ResponseEntity.ok(orders);
    }
}
