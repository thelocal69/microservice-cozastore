package com.cozastore.carouselservice.controller;

import com.cozastore.carouselservice.annotation.RequiredAuthorization;
import com.cozastore.carouselservice.dto.CarouselDTO;
import com.cozastore.carouselservice.service.ICarouselService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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

    @RequiredAuthorization("ROLE_ADMIN")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<?> createCarousel(@RequestBody CarouselDTO carouselDTO){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.info("Created carousel is completed !");
        return carouselService.createCarousel(carouselDTO, request);
    }

    @RequiredAuthorization("ROLE_ADMIN")
    @DeleteMapping("/{carouselId}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<?> deleteCarousel(@PathVariable String carouselId){
        log.info("Delete carousel is completed !");
        return carouselService.deleteCarousel(carouselId);
    }
}
