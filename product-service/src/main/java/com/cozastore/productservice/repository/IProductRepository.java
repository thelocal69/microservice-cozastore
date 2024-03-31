package com.cozastore.productservice.repository;

import com.cozastore.productservice.entity.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends MongoRepository<ProductEntity, String> {
    List<ProductEntity> findAllByCategory_Id(String categoryId, Pageable pageable);
    int countAllByCategory_Id(String categoryId);
    List<ProductEntity> findAllByCategory_Slug(String slug, Pageable pageable);
    int countAllByCategory_Slug(String slug);
}
