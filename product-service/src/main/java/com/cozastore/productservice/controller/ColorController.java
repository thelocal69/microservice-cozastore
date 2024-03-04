package com.cozastore.productservice.controller;

import com.cozastore.productservice.dto.ColorDTO;
import com.cozastore.productservice.service.IColorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/color")
public class ColorController {

    private final IColorService colorService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Transactional(readOnly = true)
    public CompletableFuture<?> getAll(int page, int limit){
        log.info("Get All Color is completed !");
        return colorService.getAll(page, limit);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<Void> createColor(@RequestBody ColorDTO colorDTO){
        log.info("Created Color is completed !");
        return colorService.createColor(colorDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<Void> deleteColor(@PathVariable("id") String id){
        log.info("Delete Color is completed !");
        return colorService.deleteColor(id);
    }
}
