package com.cozastore.userservice.repository;

import com.cozastore.userservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findOneById(Long id);
    Boolean existsByEmail(String email);
}
