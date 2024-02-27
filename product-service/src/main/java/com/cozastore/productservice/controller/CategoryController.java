package com.cozastore.productservice.controller;

import com.cozastore.productservice.dto.CategoryDTO;
import com.cozastore.productservice.payload.ObjectResponse;
import com.cozastore.productservice.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
@Slf4j
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService categoryService;

    @GetMapping()
    @Transactional(readOnly = true)
    public ResponseEntity<ObjectResponse> getAll(){
        log.info("get all category is completed !");
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ObjectResponse(
                                200,
                                "get all category is completed !",
                                this.categoryService.getAll()
                        )
                );
    }

    @PostMapping()
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ObjectResponse> createCategory(@RequestBody CategoryDTO categoryDTO){
        this.categoryService.createCategory(categoryDTO);
        log.info("Create category is completed !");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        new ObjectResponse(
                                201,
                                "Create category is completed !",
                                ""
                        )
                );
    }
}
