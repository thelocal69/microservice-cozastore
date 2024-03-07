package com.cozastore.carouselservice.service;

import com.cozastore.carouselservice.dto.CarouselDTO;
import com.cozastore.carouselservice.entity.CarouselEntity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ICarouselService {
    CompletableFuture<List<CarouselDTO>> getAll();
    CompletableFuture<Void> createCarousel(CarouselDTO carouselDTO);
    CompletableFuture<Void> deleteCarousel(String id);
}
