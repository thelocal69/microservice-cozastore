package com.cozastore.productservice.converter;

import com.cozastore.productservice.dto.ProductDTO;
import com.cozastore.productservice.entity.ProductEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductConverter {
    public ProductDTO toProductDTO(ProductEntity productModel){
        return ProductDTO.builder()
                .id(productModel.getId())
                .productName(productModel.getProductName())
                .price(productModel.getPrice())
                .imageUrl(productModel.getImageUrl())
                .quantity(productModel.getQuantity())
                .description(productModel.getDescription())
                .category(productModel.getCategory().getSlug())
                .color(productModel.getColor().getName())
                .size(productModel.getSize().getName())
                .build();
    }
    public List<ProductDTO> toListProductDTO(List<ProductEntity> productModelList){
        return productModelList.stream().map(this::toProductDTO).collect(Collectors.toList());
    }

    public ProductEntity toProductModel(ProductDTO productDTO){
        ProductEntity productModel = new ProductEntity();
        productModel.setId(productDTO.getId());
        productModel.setProductName(productDTO.getProductName());
        productModel.setPrice(productDTO.getPrice());
        productModel.setImageUrl(productDTO.getImageUrl());
        productModel.setDescription(productDTO.getDescription());
        productModel.setQuantity(productDTO.getQuantity());
        productModel.setStatus(productDTO.getStatus());
        return productModel;
    }

    public ProductEntity updateProduct(ProductEntity productModel, ProductDTO productDTO){
        productModel.setProductName(productDTO.getProductName());
        productModel.setPrice(productDTO.getPrice());
        productModel.setImageUrl(productDTO.getImageUrl());
        productModel.setDescription(productDTO.getDescription());
        productModel.setQuantity(productDTO.getQuantity());
        productModel.setStatus(productDTO.getStatus());
        return productModel;
    }
}
