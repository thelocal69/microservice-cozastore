package com.cozastore.productservice.converter;

import com.cozastore.productservice.dto.CategoryDTO;
import com.cozastore.productservice.model.CategoryModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryConverter {
    public CategoryDTO toCategoryDTO(CategoryModel categoryModel){
        return CategoryDTO
                .builder()
                .id(categoryModel.getId())
                .name(categoryModel.getName())
                .build();
    }

    public List<CategoryDTO> toListCategoryDTO(List<CategoryModel> categoryModelList){
        return categoryModelList.stream().map(this::toCategoryDTO).collect(Collectors.toList());
    }

    public CategoryModel toCategoryModel(CategoryDTO categoryDTO){
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setId(categoryDTO.getId());
        categoryModel.setName(categoryDTO.getName());
        return categoryModel;
    }
}
