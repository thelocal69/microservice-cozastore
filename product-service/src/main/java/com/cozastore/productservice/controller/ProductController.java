package com.cozastore.productservice.controller;

import com.cozastore.productservice.dto.ProductDTO;
import com.cozastore.productservice.service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/api/product")
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Transactional(readOnly = true)
    public CompletableFuture<?> getAllProduct(
            @RequestParam int page,
            @RequestParam int limit
    ){
        log.info("get product is completed !");
        return productService.getAllProduct(page, limit);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<?> createdProduct(@RequestBody ProductDTO productDTO){
        log.info("Created product is completed !");
        return productService.upsert(productDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<?> updateProduct(@PathVariable("id") String id, @RequestBody ProductDTO productDTO){
        productDTO.setId(id);
        log.info("Created product is completed !");
        return productService.upsert(productDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<?> deleteProduct(@PathVariable("id") String id){
        log.info("Delete product is completed !");
        return productService.delete(id);
    }
}
