package com.cozastore.productservice.converter;

import com.cozastore.productservice.dto.CategoryDTO;
import com.cozastore.productservice.entity.CategoryEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryConverter {
    public CategoryDTO toCategoryDTO(CategoryEntity categoryModel){
        return CategoryDTO
                .builder()
                .id(categoryModel.getId())
                .name(categoryModel.getName())
                .build();
    }

    public List<CategoryDTO> toListCategoryDTO(List<CategoryEntity> categoryModelList){
        return categoryModelList.stream().map(this::toCategoryDTO).collect(Collectors.toList());
    }

    public CategoryEntity toCategoryModel(CategoryDTO categoryDTO){
        CategoryEntity categoryModel = new CategoryEntity();
        categoryModel.setId(categoryDTO.getId());
        categoryModel.setName(categoryDTO.getName());
        return categoryModel;
    }
}
