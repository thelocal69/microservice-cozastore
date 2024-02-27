package com.cozastore.productservice.repository;

import com.cozastore.productservice.model.ColorModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IColorRepository extends MongoRepository<ColorModel, String> {
}
