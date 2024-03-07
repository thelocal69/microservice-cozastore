package com.cozastore.carouselservice.repository;

import com.cozastore.carouselservice.entity.CarouselEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICarouselRepository extends MongoRepository<CarouselEntity, String> {
}
