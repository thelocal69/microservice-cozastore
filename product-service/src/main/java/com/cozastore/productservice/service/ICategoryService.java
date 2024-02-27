package com.cozastore.productservice.service;

import com.cozastore.productservice.dto.CategoryDTO;

import java.util.List;

public interface ICategoryService {
    List<CategoryDTO> getAll();
    void createCategory(CategoryDTO categoryDTO);
}
