package com.cozastore.orderservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private String orderNo;
    private Long userId;
    private Long productId;
    private BigDecimal price;private int quantity;
    private int status;
}
