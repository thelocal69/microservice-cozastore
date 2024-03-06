package com.cozastore.mediaservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

public interface IUploadLocalImageService {
    CompletableFuture<String> uploadImageProduct(MultipartFile file);
    CompletableFuture<byte[]> readImageUrl(String fileName);
}
