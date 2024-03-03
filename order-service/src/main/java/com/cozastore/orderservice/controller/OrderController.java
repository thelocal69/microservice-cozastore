package com.cozastore.orderservice.controller;

import com.cozastore.orderservice.dto.OrderDTO;
import com.cozastore.orderservice.payload.ResponseObject;
import com.cozastore.orderservice.service.IOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final IOrderService orderService;

    @PostMapping()
    public ResponseEntity<ResponseObject> createdOrder(@RequestBody OrderDTO orderDTO){
        this.orderService.createOrder(orderDTO);
        log.info("Place order is completed !");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        new ResponseObject(
                                201,
                                "Place order is completed !",
                                ""
                        )
                );
    }
}
