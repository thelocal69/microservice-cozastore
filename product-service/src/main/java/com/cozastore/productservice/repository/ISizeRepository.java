package com.cozastore.productservice.repository;

import com.cozastore.productservice.entity.SizeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISizeRepository extends MongoRepository<SizeEntity, String> {
    Boolean existsByName(String name);
    SizeEntity findByName(String name);
}
