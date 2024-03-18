package com.cozastore.productservice.service;

import com.cozastore.productservice.dto.ProductDTO;
import com.cozastore.productservice.payload.ResponseOutput;

import java.util.concurrent.CompletableFuture;

public interface IProductService {
    CompletableFuture<ResponseOutput> getAllProduct(int page, int limit);
    CompletableFuture<ResponseOutput> getAllProductByCategory(String categoryId ,int page, int limit);
    CompletableFuture<Void> upsert(ProductDTO productDTO);
    CompletableFuture<Void> delete(String id);
    CompletableFuture<Boolean> getProductId(String id);
}
