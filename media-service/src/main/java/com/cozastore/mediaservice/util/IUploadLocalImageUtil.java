package com.cozastore.mediaservice.util;

import org.springframework.web.multipart.MultipartFile;

public interface IUploadLocalImageUtil {
    Boolean isImageFile(MultipartFile multipartFile);
    String storeFile(MultipartFile multipartFile, String folderName);
    byte[] readFileContent(String fileName, String folderName);
    String uploadToCloud(MultipartFile file);
}
