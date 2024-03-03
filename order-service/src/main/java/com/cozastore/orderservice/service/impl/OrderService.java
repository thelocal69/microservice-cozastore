package com.cozastore.orderservice.service.impl;

import com.cozastore.orderservice.converter.OrderConverter;
import com.cozastore.orderservice.dto.OrderDTO;
import com.cozastore.orderservice.entity.OrderEntity;
import com.cozastore.orderservice.repository.IOrderRepository;
import com.cozastore.orderservice.service.IOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final IOrderRepository orderRepository;
    private final OrderConverter orderConverter;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOrder(OrderDTO orderDTO) {
        OrderEntity order = orderConverter.toOrderEntity(orderDTO);
        this.orderRepository.save(order);
        log.info("Created order is completed !");
    }
}
