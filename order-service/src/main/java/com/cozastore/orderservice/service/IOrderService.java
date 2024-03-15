package com.cozastore.orderservice.service;

import com.cozastore.orderservice.dto.OrderDTO;
import com.cozastore.orderservice.payload.ResponseOutput;
import jakarta.servlet.http.HttpServletRequest;

import java.util.concurrent.CompletableFuture;

public interface IOrderService {

    CompletableFuture<ResponseOutput> getAllOrder(Long userId, int page, int limit);
    CompletableFuture<Void> createOrder(OrderDTO orderDTO, HttpServletRequest request);
}
