package com.cozastore.productservice.service;

import com.cozastore.productservice.dto.CategoryDTO;
import com.cozastore.productservice.payload.ResponseObject;
import com.cozastore.productservice.payload.ResponseOutput;

import java.util.concurrent.CompletableFuture;

public interface ICategoryService {
    CompletableFuture<ResponseOutput> getAll(int page, int limit);
    CompletableFuture<ResponseObject> getAll();
    CompletableFuture<Boolean> getCategoryId(String id);
    CompletableFuture<Void> createCategory(CategoryDTO categoryDTO);
    CompletableFuture<Void> deleteCategory(String id);
}
