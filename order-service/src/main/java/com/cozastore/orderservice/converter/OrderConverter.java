package com.cozastore.orderservice.converter;

import com.cozastore.orderservice.dto.OrderDTO;
import com.cozastore.orderservice.entity.OrderEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrderConverter {

    public OrderEntity toOrderEntity(OrderDTO orderDTO){
        OrderEntity order = new OrderEntity();
        order.setOrderNo(UUID.randomUUID().toString().replace("-", "").toUpperCase());
        order.setPrice(orderDTO.getPrice());
        order.setQuantity(orderDTO.getQuantity());
        order.setStatus(orderDTO.getStatus());
        order.setUserId(orderDTO.getUserId());
        order.setProductId(orderDTO.getProductId());
        return  order;
    }
}
