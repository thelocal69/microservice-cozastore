package com.cozastore.productservice.service.impl;

import com.cozastore.productservice.converter.CategoryConverter;
import com.cozastore.productservice.dto.CategoryDTO;
import com.cozastore.productservice.repository.ICategoryRepository;
import com.cozastore.productservice.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryConverter categoryConverter;
    private final ICategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getAll() {
        if (this.categoryRepository.findAll().isEmpty()){
            log.info("List Category is empty !");
            throw new RuntimeException("List Category is empty !");
        }
        return categoryConverter.toListCategoryDTO(
                categoryRepository.findAll()
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createCategory(CategoryDTO categoryDTO) {
        if (categoryRepository.existsByName(categoryDTO.getName())){
            log.info("Duplicated category name !");
            throw new RuntimeException("Duplicated category name !");
        }
        this.categoryRepository.save(
                categoryConverter.toCategoryModel(categoryDTO)
        );
        log.info("Created Category is completed !");
    }
}
