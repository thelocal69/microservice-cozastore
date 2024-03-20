package com.cozastore.userservice.repository;

import com.cozastore.userservice.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserDetailRepository extends MongoRepository<UserEntity, String> {
    UserEntity findOneById(String id);
    UserEntity findOneByEmail(String email);
}
