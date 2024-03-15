package com.cozastore.cartservice.converter;

import com.cozastore.cartservice.dto.CartDTO;
import com.cozastore.cartservice.entity.CartEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartConverter {
    public CartDTO toCartDTO(CartEntity cartEntity){
        return CartDTO
                .builder()
                .id(cartEntity.getId())
                .quantity(cartEntity.getQuantity())
                .productId(cartEntity.getProductId())
                .userId(cartEntity.getUserId())
                .build();
    }

    public List<CartDTO> toCartDTOList(List<CartEntity> cartEntityList){
        return cartEntityList.stream().map(this::toCartDTO).collect(Collectors.toList());
    }

    public CartEntity toCartEntity(CartDTO cartDTO){
        CartEntity cartEntity = new CartEntity();
        cartEntity.setQuantity(cartDTO.getQuantity());
        return cartEntity;
    }
}
