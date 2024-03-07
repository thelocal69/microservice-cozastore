package com.cozastore.productservice.controller;

import com.cozastore.productservice.dto.SizeDTO;
import com.cozastore.productservice.service.ISizeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/size")
public class SizeController {

    private final ISizeService sizeService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Transactional(readOnly = true)
    public CompletableFuture<?> getAll(int page, int limit){
        log.info("Get all size is completed !");
        return sizeService.getAll(page, limit);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<?> createSize(@RequestBody SizeDTO sizeDTO){
        log.info("Created size is completed !");
        return sizeService.createSize(sizeDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<?> deleteSize(@PathVariable("id") String id){
        log.info("Delete size is completed !");
        return sizeService.deleteSize(id);
    }
}
