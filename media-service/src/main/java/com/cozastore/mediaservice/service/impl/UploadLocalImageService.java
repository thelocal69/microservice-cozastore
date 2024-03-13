package com.cozastore.mediaservice.service.impl;

import com.cozastore.mediaservice.service.IUploadLocalImageService;
import com.cozastore.mediaservice.util.IUploadLocalImageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class UploadLocalImageService implements IUploadLocalImageService {

    private final IUploadLocalImageUtil uploadLocalImageUtil;
    @Value("${path.domain_url}")
    private String domainURL;

    @Async
    @Override
    public CompletableFuture<String> uploadImageProduct(MultipartFile file) {
        log.info("Upload product image is completed !");
        return CompletableFuture.supplyAsync(
                () -> {
                    String folderName = "product";
                    String fileName = uploadLocalImageUtil.storeFile(file, folderName);
                    return domainURL+"/api/media/"+folderName+"/"+fileName;
                }
        );
    }

    @Async
    @Override
    public CompletableFuture<String> uploadImageCarousel(MultipartFile file) {
        log.info("Upload carousel image is completed !");
        return CompletableFuture.supplyAsync(
                () -> {
                    String folderName = "carousel";
                    String fileName = uploadLocalImageUtil.storeFile(file, folderName);
                    return domainURL+"/api/media/"+folderName+"/"+fileName;
                }
        );
    }

    @Async
    @Override
    public CompletableFuture<String> uploadImageUser(MultipartFile file) {
        log.info("Upload user image is completed !");
        return CompletableFuture.supplyAsync(
                () -> {
                    String folderName = "user";
                    String fileName = uploadLocalImageUtil.storeFile(file, folderName);
                    return domainURL+"/api/media/"+folderName+"/"+fileName;
                }
        );
    }

    @Async
    @Override
    public CompletableFuture<byte[]> readImageUrl(String fileName, String folderName) {
        log.info("Read product image is completed !");
        return CompletableFuture.supplyAsync(
                () -> uploadLocalImageUtil.readFileContent(fileName, folderName)
        ) ;
    }
}
