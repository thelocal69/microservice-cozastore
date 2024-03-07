package com.cozastore.orderservice.service;

import com.cozastore.orderservice.dto.OrderDTO;

public interface IOrderService {
    void createOrder(OrderDTO orderDTO);
}
