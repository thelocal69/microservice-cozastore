package com.cozastore.carouselservice.service;

import com.cozastore.carouselservice.dto.CarouselDTO;
import com.cozastore.carouselservice.entity.CarouselEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ICarouselService {
    CompletableFuture<List<CarouselDTO>> getAll();
    CompletableFuture<Void> createCarousel(CarouselDTO carouselDTO, HttpServletRequest request);
    CompletableFuture<Void> deleteCarousel(String id);
}
