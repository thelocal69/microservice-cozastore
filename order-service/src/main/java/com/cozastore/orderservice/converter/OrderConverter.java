package com.cozastore.orderservice.converter;

import com.cozastore.orderservice.dto.OrderDTO;
import com.cozastore.orderservice.entity.OrderEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderConverter {

    public OrderDTO toOrderDTO(OrderEntity orderEntity){
        return OrderDTO
                .builder()
                .id(orderEntity.getId())
                .orderNo(orderEntity.getOrderNo())
                .price(orderEntity.getPrice())
                .quantity(orderEntity.getQuantity())
                .userId(orderEntity.getUserId())
                .productId(orderEntity.getProductId())
                .status(orderEntity.getStatus())
                .createdDate(orderEntity.getCreatedDate())
                .build();
    }

    public List<OrderDTO> toOrderDTOList(List<OrderEntity> orderEntityList){
        return orderEntityList.stream().map(this::toOrderDTO).collect(Collectors.toList());
    }

    public OrderEntity toOrderEntity(OrderDTO orderDTO){
        OrderEntity order = new OrderEntity();
        order.setOrderNo(UUID.randomUUID().toString().replace("-", "").toUpperCase());
        order.setPrice(orderDTO.getPrice());
        order.setQuantity(orderDTO.getQuantity());
        order.setStatus(orderDTO.getStatus());
        return  order;
    }
}
