package com.cozastore.blogservice.repository;

import com.cozastore.blogservice.entity.TagEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITagRepository extends MongoRepository<TagEntity, String> {
}
