package com.cozastore.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "product")
public class ProductModel extends AbstractAuditingModel{
    @Id
    @Indexed(unique=true)
    private String id;
    private String productName;
    private String imageUrl;
    private BigDecimal price;
    private String description;
    private int quantity;
    private int status;
    private List<CategoryModel> category;
    private List<SizeModel> size;
    private List<ColorModel> color;
}
