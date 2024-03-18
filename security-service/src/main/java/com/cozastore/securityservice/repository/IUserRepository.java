package com.cozastore.securityservice.repository;

import com.cozastore.securityservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByEmailAndStatus(String email, int status);
    Boolean existsByEmailAndStatus(String email, int status);
    UserEntity findByEmail(String email);
}
