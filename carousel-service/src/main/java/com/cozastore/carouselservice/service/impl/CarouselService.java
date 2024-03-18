package com.cozastore.carouselservice.service.impl;

import com.cozastore.carouselservice.converter.CarouselConverter;
import com.cozastore.carouselservice.dto.CarouselDTO;
import com.cozastore.carouselservice.entity.CarouselEntity;
import com.cozastore.carouselservice.exception.NotFoundException;
import com.cozastore.carouselservice.feign.ICategoryClient;
import com.cozastore.carouselservice.repository.ICarouselRepository;
import com.cozastore.carouselservice.service.ICarouselService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class CarouselService implements ICarouselService {

    private final ICarouselRepository carouselRepository;
    private final CarouselConverter carouselConverter;
    private final ICategoryClient categoryClient;

    @Async
    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<List<CarouselDTO>> getAll() {
        return CompletableFuture.supplyAsync(
                () -> {
                    if (this.carouselRepository.findAll().isEmpty()){
                        log.info("List carousel is empty !");
                        throw new NotFoundException("List carousel is empty !");
                    }
                    return this.carouselConverter.toCarouselDTOList(
                            carouselRepository.findAll()
                    );
                }
        );
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<Void> createCarousel(CarouselDTO carouselDTO, HttpServletRequest request) {
        return CompletableFuture.supplyAsync(
                () -> {
                    CarouselEntity carouselEntity = carouselConverter.toCarouselEntity(carouselDTO);
                    if (categoryClient.existCategoryId(request.getHeader("Authorization"), carouselDTO.getCategoryId())){
                        carouselEntity.setCategoryId(
                                carouselDTO.getCategoryId()
                        );
                        this.carouselRepository.save(carouselEntity);
                        log.info("Create carousel is completed !");
                    }
                    return null;
                }
        );
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<Void> deleteCarousel(String id) {
        return CompletableFuture.supplyAsync(
                () -> {
                    if (!this.carouselRepository.existsById(id)){
                        log.info("Cannot delete carousel ! Carousel is not exist !");
                        throw new NotFoundException("Carousel not exist !");
                    }
                    this.carouselRepository.deleteById(id);
                    log.info("Delete carousel is completed !");
                    return  null;
                }
        );
    }
}
