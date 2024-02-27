package com.cozastore.productservice.controller;

import com.cozastore.productservice.dto.ProductDTO;
import com.cozastore.productservice.payload.ObjectResponse;
import com.cozastore.productservice.service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/product")
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

    @GetMapping()
    @Transactional(readOnly = true)
    public ResponseEntity<ObjectResponse> getAllProduct(){
        log.info("Get all is completed !");
        return ResponseEntity.status(HttpStatus.OK).body(
                new ObjectResponse(
                    200,
                        "Get all is completed !",
                        this.productService.getAllProduct()
                )
        );
    }

    @PostMapping()
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ObjectResponse> createdProduct(@RequestBody ProductDTO productDTO){
        this.productService.upsert(productDTO);
        log.info("Created product is completed !");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        new ObjectResponse(
                                201,
                                "Created product is completed !",
                                ""
                        )
                );
    }
}
