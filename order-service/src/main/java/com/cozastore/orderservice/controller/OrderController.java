package com.cozastore.orderservice.controller;

import com.cozastore.orderservice.annotation.RequiredAuthorization;
import com.cozastore.orderservice.dto.OrderDTO;
import com.cozastore.orderservice.service.IOrderService;
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
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final IOrderService orderService;

    @RequiredAuthorization("ROLE_USER")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Transactional(readOnly = true)
    public CompletableFuture<?> getAllOrder(
            @RequestParam Long userId,
            @RequestParam int page,
            @RequestParam int limit
    ){
        log.info("Get all order is completed !");
        return orderService.getAllOrder(userId, page, limit);
    }

    @RequiredAuthorization("ROLE_USER")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<?> createdOrder(@RequestBody OrderDTO orderDTO){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.info("Place order is completed !");
        return orderService.createOrder(orderDTO, request);
    }
}
