package com.artland.start.ecommercebackend.service;


import com.artland.start.ecommercebackend.model.LocalUser;
import com.artland.start.ecommercebackend.model.WebOrder;

import java.util.List;

public interface OrderService {

    List<WebOrder> getOrders(LocalUser localUser);
}
