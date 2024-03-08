package com.cozastore.securityservice.repository;

import com.cozastore.securityservice.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends MongoRepository<UserEntity, String> {
}