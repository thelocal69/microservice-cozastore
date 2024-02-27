package com.cozastore.productservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private String id;
    private String productName;
    private String imageUrl;
    private BigDecimal price;
    private String description;
    private int quantity;
    private int status;
    private String category;
    private String size;
    private String color;
}
