package com.cozastore.productservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "product")
public class ProductEntity extends AbstractAuditingEntity {
    @Id
    @Indexed(unique=true)
    private String id;
    @Field(name = "productName")
    private String productName;
    @Field(name = "imageUrl")
    private String imageUrl;
    @Field(name = "price")
    private BigDecimal price;
    @Field(name = "description")
    private String description;
    @Field(name = "quantity")
    private int quantity;
    @Field(name = "status")
    private int status;
    @Field(name = "category")
    private CategoryEntity category;
    @Field(name = "size")
    private SizeEntity size;
    @Field(name = "color")
    private ColorEntity color;
}
