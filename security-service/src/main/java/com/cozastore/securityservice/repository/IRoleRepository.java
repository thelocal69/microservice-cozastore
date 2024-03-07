package com.cozastore.securityservice.repository;

import com.cozastore.securityservice.entity.RoleEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends MongoRepository<RoleEntity, String> {
}
