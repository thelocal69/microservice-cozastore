package com.cozastore.blogservice.repository;

import com.cozastore.blogservice.entity.CommentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICommentRepository extends MongoRepository<CommentEntity, String> {
}
