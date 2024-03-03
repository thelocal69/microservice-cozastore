package com.cozastore.productservice.repository;

import com.cozastore.productservice.entity.CategoryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends MongoRepository<CategoryEntity, String> {
    Boolean existsByName(String name);
    CategoryEntity findByName(String name);
}
