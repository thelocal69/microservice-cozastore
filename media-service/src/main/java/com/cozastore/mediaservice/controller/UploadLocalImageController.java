package com.cozastore.mediaservice.controller;

import com.cozastore.mediaservice.annotation.Authenticate;
import com.cozastore.mediaservice.annotation.RequiredAuthorization;
import com.cozastore.mediaservice.service.IUploadLocalImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
@RequestMapping("/api/media")
@RequiredArgsConstructor
public class UploadLocalImageController {

    private final IUploadLocalImageService uploadLocalImageService;

    @RequiredAuthorization("ROLE_ADMIN")
    @PostMapping(value = "/product")
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<String> uploadProductImage(@RequestParam("fileName")MultipartFile file){
        log.info("Upload image product is completed !");
        return this.uploadLocalImageService.uploadImageProduct(file);
    }

    @RequiredAuthorization("ROLE_ADMIN")
    @PostMapping(value = "/carousel")
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<String> uploadCarouselImage(@RequestParam("fileName")MultipartFile file){
        log.info("Upload image carousel is completed !");
        return this.uploadLocalImageService.uploadImageCarousel(file);
    }

    @Authenticate
    @PostMapping(value = "/user")
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<String> uploadUserImage(@RequestParam("fileName")MultipartFile file){
        log.info("Upload image user is completed !");
        return this.uploadLocalImageService.uploadImageUser(file);
    }

    @Authenticate
    @PostMapping(value = "/cloud")
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<String> uploadImageToCloud(@RequestParam("fileName")MultipartFile file){
        log.info("Upload image to cloud is completed !");
        return this.uploadLocalImageService.uploadImageToCloud(file);
    }

    @GetMapping(value = "/{folderName}/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public CompletableFuture<?> readImageUrl(
            @PathVariable String fileName,
            @PathVariable String folderName){
        log.info("Read image product is completed !");
        return uploadLocalImageService.readImageUrl(fileName, folderName);
    }
}
