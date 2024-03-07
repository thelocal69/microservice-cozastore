package com.cozastore.productservice.repository;

import com.cozastore.productservice.entity.ColorEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IColorRepository extends MongoRepository<ColorEntity, String> {
    Boolean existsByName(String name);
    ColorEntity findByName(String name);
}
