package com.cozastore.productservice.converter;

import com.cozastore.productservice.dto.ProductDTO;
import com.cozastore.productservice.model.CategoryModel;
import com.cozastore.productservice.model.ColorModel;
import com.cozastore.productservice.model.ProductModel;
import com.cozastore.productservice.model.SizeModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductConverter {
    public ProductDTO toProductDTO(ProductModel productModel){
        return ProductDTO.builder()
                .id(productModel.getId())
                .productName(productModel.getProductName())
                .price(productModel.getPrice())
                .imageUrl(productModel.getImageUrl())
                .quantity(productModel.getQuantity())
                .description(productModel.getDescription())
                .category(
                        productModel
                                .getCategory()
                                .stream()
                                .map(CategoryModel::getName)
                                .toString()
                )
                .status(productModel.getStatus())
                .color(
                        productModel
                                .getColor()
                                .stream()
                                .map(ColorModel::getName)
                                .toString()
                )
                .size(
                        productModel
                                .getSize()
                                .stream()
                                .map(SizeModel::getName)
                                .toString()
                )
                .build();
    }
    public List<ProductDTO> toListProductDTO(List<ProductModel> productModelList){
        return productModelList.stream().map(this::toProductDTO).collect(Collectors.toList());
    }

    public ProductModel toProductModel(ProductDTO productDTO){
        ProductModel productModel = new ProductModel();
        productModel.setId(productDTO.getId());
        productModel.setProductName(productDTO.getProductName());
        productModel.setPrice(productDTO.getPrice());
        productModel.setImageUrl(productDTO.getImageUrl());
        productModel.setDescription(productDTO.getDescription());
        productModel.setQuantity(productDTO.getQuantity());
        productModel.setStatus(productDTO.getStatus());
        return productModel;
    }

    public ProductModel updateProduct(ProductModel productModel, ProductDTO productDTO){
        productModel.setProductName(productDTO.getProductName());
        productModel.setPrice(productDTO.getPrice());
        productModel.setImageUrl(productDTO.getImageUrl());
        productModel.setDescription(productDTO.getDescription());
        productModel.setQuantity(productDTO.getQuantity());
        productModel.setStatus(productDTO.getStatus());
        return productModel;
    }
}
