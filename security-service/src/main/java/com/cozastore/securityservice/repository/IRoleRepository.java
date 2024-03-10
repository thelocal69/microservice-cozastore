package com.cozastore.securityservice.repository;

import com.cozastore.securityservice.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<RoleEntity, String> {
    RoleEntity findByRoleName(String roleName);
    Boolean existsByRoleName(String roleName);
}
