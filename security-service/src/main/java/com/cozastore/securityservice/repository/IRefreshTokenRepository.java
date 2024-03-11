package com.cozastore.securityservice.repository;

import com.cozastore.securityservice.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRefreshTokenRepository extends JpaRepository<TokenEntity, Long> {

    @Query(" SELECT r FROM refresh_token r JOIN users u ON r.user.id = u.id" +
            "  WHERE u.id = :userId AND (r.expired = false or r.revoke = false )")
    List<TokenEntity> findAllByTokenValidByUser(Long userId);

    TokenEntity findByToken(String token);
}
