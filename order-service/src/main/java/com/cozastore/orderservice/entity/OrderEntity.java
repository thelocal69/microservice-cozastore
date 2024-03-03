package com.cozastore.orderservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "order_product")
public class OrderEntity extends AbstractAuditingEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "order_no")
    private String orderNo;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "status")
    private int status;


}
