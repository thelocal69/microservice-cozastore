package com.cozastore.productservice.service;

import com.cozastore.productservice.dto.SizeDTO;
import com.cozastore.productservice.payload.ResponseOutput;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ISizeService {
    CompletableFuture<ResponseOutput> getAll(int page, int limit);
    CompletableFuture<Void> createSize(SizeDTO sizeDTO);
    CompletableFuture<Void> deleteSize(String id);
}
