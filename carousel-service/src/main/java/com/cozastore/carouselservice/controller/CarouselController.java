package com.cozastore.carouselservice.controller;

import com.cozastore.carouselservice.dto.CarouselDTO;
import com.cozastore.carouselservice.service.ICarouselService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/carousel")
public class CarouselController {

    private final ICarouselService carouselService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Transactional(readOnly = true)
    public CompletableFuture<?> getAllCarousel(){
        log.info("Get list carousel is completed !");
        return carouselService.getAll();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<?> createCarousel(@RequestBody CarouselDTO carouselDTO){
        log.info("Created carousel is completed !");
        return carouselService.createCarousel(carouselDTO);
    }

    @DeleteMapping("/{carouselId}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<?> deleteCarousel(@PathVariable String carouselId){
        log.info("Delete carousel is completed !");
        return carouselService.deleteCarousel(carouselId);
    }
}
