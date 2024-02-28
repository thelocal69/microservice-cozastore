package com.cozastore.productservice.service;

import com.cozastore.productservice.dto.ProductDTO;

import java.util.List;

public interface IProductService {
    List<ProductDTO> getAllProduct();
    ProductDTO getProductById(Long id);
    void upsert(ProductDTO productDTO);
    void delete(ProductDTO productDTO);
}
