package com.cozastore.cartservice.repository;

import com.cozastore.cartservice.entity.CartEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICartRepository extends MongoRepository<CartEntity, String> {
    List<CartEntity> findAllByUserId(String userId, Pageable pageable);
    int countAllByUserId(String userId);
    CartEntity findOneByIdAndUserId(String id, String userId);
}
