package com.cozastore.orderservice.service.impl;

import com.cozastore.orderservice.converter.OrderConverter;
import com.cozastore.orderservice.dto.OrderDTO;
import com.cozastore.orderservice.entity.OrderEntity;
import com.cozastore.orderservice.feign.IProductClient;
import com.cozastore.orderservice.feign.IUserClient;
import com.cozastore.orderservice.repository.IOrderRepository;
import com.cozastore.orderservice.service.IOrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final IOrderRepository orderRepository;
    private final OrderConverter orderConverter;
    private final IProductClient productClient;
    private final IUserClient userClient;

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<Void> createOrder(OrderDTO orderDTO, HttpServletRequest request) {
        return CompletableFuture.supplyAsync(
                () -> {
                    OrderEntity order = orderConverter.toOrderEntity(orderDTO);
                    if (productClient.checkProduct(
                           request.getHeader("Authorization") ,orderDTO.getProductId())){
                        order.setProductId(orderDTO.getProductId());
                    }
                    if (userClient.checkUserId(request.getHeader("Authorization") ,orderDTO.getUserId())){
                        order.setUserId(orderDTO.getUserId());
                    }
                    this.orderRepository.save(order);
                    log.info("Created order is completed !");
                    return  null;
                }
        );
    }
}
