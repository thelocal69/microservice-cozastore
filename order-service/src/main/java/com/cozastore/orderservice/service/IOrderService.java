package com.cozastore.orderservice.service;

import com.cozastore.orderservice.dto.OrderDTO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.concurrent.CompletableFuture;

public interface IOrderService {
    CompletableFuture<Void> createOrder(OrderDTO orderDTO, HttpServletRequest request);
}
