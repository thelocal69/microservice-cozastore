package com.cozastore.productservice.repository;

import com.cozastore.productservice.model.SizeModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISizeRepository extends MongoRepository<SizeModel, String> {
}
