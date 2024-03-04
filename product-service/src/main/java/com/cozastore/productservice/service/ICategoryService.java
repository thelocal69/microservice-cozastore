package com.cozastore.productservice.service;

import com.cozastore.productservice.dto.CategoryDTO;
import com.cozastore.productservice.payload.ResponseOutput;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ICategoryService {
    CompletableFuture<ResponseOutput> getAll(int page, int limit);
    CompletableFuture<Void> createCategory(CategoryDTO categoryDTO);
    CompletableFuture<Void> deleteCategory(String id);
}
