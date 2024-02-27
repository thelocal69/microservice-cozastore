package com.cozastore.productservice.repository;

import com.cozastore.productservice.model.CategoryModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends MongoRepository<CategoryModel, String> {
    Boolean existsByName(String name);
}
