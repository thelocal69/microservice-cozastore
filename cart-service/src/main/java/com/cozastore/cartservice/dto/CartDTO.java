package com.cozastore.cartservice.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private String id;
    private int quantity;
    private String productId;
    private Long userId;
}
