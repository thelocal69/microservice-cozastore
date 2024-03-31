package com.cozastore.productservice.controller;

import com.cozastore.productservice.annotation.RequiredAuthorization;
import com.cozastore.productservice.dto.CategoryDTO;
import com.cozastore.productservice.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/category")
@Slf4j
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService categoryService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Transactional(readOnly = true)
    public CompletableFuture<?> getAll(
            @RequestParam int page,
            @RequestParam int limit){
        log.info("get all category is completed !");
       return categoryService.getAll(page, limit);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    @Transactional(readOnly = true)
    public CompletableFuture<?> getAllCategory(){
        log.info("get list category is completed !");
        return categoryService.getAll();
    }

    @GetMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional(readOnly = true)
    public CompletableFuture<?> existCategoryId(@PathVariable String categoryId){
        log.info("Category id is exist !");
        return categoryService.getCategoryId(categoryId);
    }

    @RequiredAuthorization("ROLE_ADMIN")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<?> createCategory(@RequestBody CategoryDTO categoryDTO){
        log.info("Create category is completed !");
        return categoryService.createCategory(categoryDTO);
    }

    @RequiredAuthorization("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<?> deleteCategory(@PathVariable("id") String id){
        log.info("Delete category is completed !");
        return categoryService.deleteCategory(id);
    }
}
