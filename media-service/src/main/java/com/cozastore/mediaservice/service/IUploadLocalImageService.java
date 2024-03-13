package com.cozastore.mediaservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

public interface IUploadLocalImageService {
    CompletableFuture<String> uploadImageProduct(MultipartFile file);
    CompletableFuture<String> uploadImageCarousel(MultipartFile file);
    CompletableFuture<String> uploadImageUser(MultipartFile file);
    CompletableFuture<byte[]> readImageUrl(String fileName, String folderName);
}
