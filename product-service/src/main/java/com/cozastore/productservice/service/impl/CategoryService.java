package com.cozastore.productservice.service.impl;

import com.cozastore.productservice.converter.CategoryConverter;
import com.cozastore.productservice.dto.CategoryDTO;
import com.cozastore.productservice.payload.ResponseOutput;
import com.cozastore.productservice.repository.ICategoryRepository;
import com.cozastore.productservice.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryConverter categoryConverter;
    private final ICategoryRepository categoryRepository;

    @Async
    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<ResponseOutput> getAll(int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        if (this.categoryRepository.findAll(pageable).isEmpty()){
            log.info("List Category is empty !");
            throw new RuntimeException("List Category is empty !");
        }
        List<CategoryDTO> categoryDTOList = categoryConverter.toListCategoryDTO(
                categoryRepository.findAll(pageable).getContent()
        );
        int totalItem = (int) categoryRepository.count();
        int totalPage = (int) Math.ceil((double) totalItem / limit);
        return CompletableFuture.supplyAsync(
                () -> new ResponseOutput(
                        page, totalPage, totalItem, categoryDTOList
                )
        );
    }

    @Async
    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<Boolean> getCategoryId(String id) {
        return CompletableFuture.supplyAsync(
                () -> {
                    if (!this.categoryRepository.existsById(id)){
                        log.info("Category id is not exist !");
                        throw new RuntimeException("Category id is not exist !");
                    }
                    return this.categoryRepository.existsById(id);
                }
        );
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<Void> createCategory(CategoryDTO categoryDTO) {
        return CompletableFuture.supplyAsync(
                () -> {
                    if (categoryRepository.existsByName(categoryDTO.getName())){
                        log.info("Duplicated category name !");
                        throw new RuntimeException("Duplicated category name !");
                    }
                    this.categoryRepository.save(
                            categoryConverter.toCategoryModel(categoryDTO)
                    );
                    log.info("Created Category is completed !");
                    return null;
                }
        );
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<Void> deleteCategory(String id) {
        return CompletableFuture.supplyAsync(
                () -> {
                    if (!categoryRepository.existsById(id)){
                        log.info("Category is not exist ! Cannot delete !");
                        throw new RuntimeException("Category is not exist ! Cannot delete !");
                    }
                    this.categoryRepository.deleteById(id);
                    log.info("Delete Category is completed !");
                    return null;
                }
        );
    }
}
