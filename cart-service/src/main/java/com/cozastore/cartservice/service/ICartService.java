package com.cozastore.cartservice.service;

import com.cozastore.cartservice.dto.CartDTO;
import com.cozastore.cartservice.payload.ResponseOutput;
import jakarta.servlet.http.HttpServletRequest;

import java.util.concurrent.CompletableFuture;

public interface ICartService {
    CompletableFuture<ResponseOutput> getAll(String userId, int page, int limit);
    CompletableFuture<Void> addCartItem(CartDTO cartDTO, HttpServletRequest request);
    CompletableFuture<Void> updateCartItem(CartDTO cartDTO);
    CompletableFuture<Void> deleteCartItem(CartDTO cartDTO);
}
