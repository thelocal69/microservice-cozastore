package com.cozastore.blogservice.repository;

import com.cozastore.blogservice.entity.BlogEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBlogRepository extends MongoRepository<BlogEntity, String> {
    BlogEntity findOneById(String id);
}
