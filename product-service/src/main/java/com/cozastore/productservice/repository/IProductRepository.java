package com.cozastore.productservice.repository;

import com.cozastore.productservice.model.ProductModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends MongoRepository<ProductModel, String> {
}
