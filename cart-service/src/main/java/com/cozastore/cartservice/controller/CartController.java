package com.cozastore.cartservice.controller;

import com.cozastore.cartservice.annotation.RequiredAuthorization;
import com.cozastore.cartservice.dto.CartDTO;
import com.cozastore.cartservice.service.ICartService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/cart")
@Slf4j
@RequiredArgsConstructor
public class CartController {

    private final ICartService cartService;

    @RequiredAuthorization("ROLE_USER")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Transactional(readOnly = true)
    public CompletableFuture<?> getAllCart(
            @RequestParam String userId,
            @RequestParam int page,
            @RequestParam int limit
    ){
        log.info("Get cart item is completed !");
        return cartService.getAll(userId, page, limit);
    }

    @RequiredAuthorization("ROLE_USER")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<?> createCartItem(@RequestBody CartDTO cartDTO){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.info("Add cart item is completed !");
        return cartService.addCartItem(cartDTO, request);
    }

    @RequiredAuthorization("ROLE_USER")
    @PutMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<?> updateCartItem(@RequestBody CartDTO cartDTO){
        log.info("Update cart item is completed !");
        return cartService.updateCartItem(cartDTO);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.OK)
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<?> deleteCartItem(@RequestBody CartDTO cartDTO){
        log.info("Delete cart item is completed !");
        return cartService.deleteCartItem(cartDTO);
    }
}
