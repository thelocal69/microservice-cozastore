package com.cozastore.cartservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "carts")
public class CartEntity extends AbstractAuditingEntity{
    @Id
    @Indexed(unique = true)
    private String id;
    @Field(name = "quantity")
    private int quantity;
    @Field("product_id")
    private String productId;
    @Field(name = "user_id")
    private Long userId;
}
