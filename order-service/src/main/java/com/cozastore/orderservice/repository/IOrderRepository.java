package com.cozastore.orderservice.repository;

import com.cozastore.orderservice.entity.OrderEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findAllByUserId(String id, Pageable pageable);
    int countAllByUserId(String userId);
}
