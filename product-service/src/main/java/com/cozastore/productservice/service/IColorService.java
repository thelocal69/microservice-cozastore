package com.cozastore.productservice.service;

import com.cozastore.productservice.dto.ColorDTO;
import com.cozastore.productservice.payload.ResponseOutput;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IColorService {
    CompletableFuture<ResponseOutput> getAll(int page, int limit);
    CompletableFuture<Void> createColor(ColorDTO colorDTO);
    CompletableFuture<Void> deleteColor(String id);
}
