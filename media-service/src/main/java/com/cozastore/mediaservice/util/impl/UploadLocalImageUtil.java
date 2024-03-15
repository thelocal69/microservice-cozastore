package com.cozastore.mediaservice.util.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.cozastore.mediaservice.exception.MediaException;
import com.cozastore.mediaservice.util.IUploadLocalImageUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UploadLocalImageUtil implements IUploadLocalImageUtil {

    @Value("${path.root_path}")
    private String rootPath;
    private final Cloudinary cloudinary;

    @Override
    public Boolean isImageFile(MultipartFile multipartFile) {
        String fileExtension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        if (fileExtension != null){
            return Arrays.asList(new String[] {"png","jpg","jpeg", "bmp"})
                    .contains(fileExtension.trim().toLowerCase());
        }
        return false;
    }

    @Override
    public String storeFile(MultipartFile multipartFile, String folderName) {
        try {
            if (multipartFile.isEmpty()) {
                throw new MediaException("Failed to store empty file !");
            }
            if(!isImageFile(multipartFile)) {
                throw new MediaException("You can only upload image file !");
            }
            float fileSizeInMegabytes = multipartFile.getSize() / 1_000_000.0f;
            if(fileSizeInMegabytes > 5.0f) {
                throw new MediaException("File must be <= 5Mb !");
            }
            String pathFolder = rootPath+folderName;
            Path storageFolder = Paths.get(pathFolder);
            if (!(Files.exists(storageFolder))){
                Files.createDirectories(storageFolder);
            }
            String fileExtension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            String generatedFileName = UUID.randomUUID().toString().replace("-", "");
            generatedFileName = generatedFileName+"."+fileExtension;
            String pathImage = pathFolder + "/" + generatedFileName;
            Path destinationFilePath = storageFolder.resolve(
                            pathImage)
                    .normalize().toAbsolutePath();
            if (!destinationFilePath.getParent().equals(storageFolder.toAbsolutePath())) {
                throw new MediaException(
                        "Cannot store file outside current directory !");
            }
            try (InputStream inputStream = multipartFile.getInputStream()) {
                Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
            return generatedFileName;
        }
        catch (IOException exception) {
            throw new RuntimeException("Failed to store file !", exception);
        }
    }

    @Override
    public byte[] readFileContent(String fileName, String folderName) {
        try {
            String pathFolder = rootPath+folderName;
            Path storageFolder = Paths.get(pathFolder);
            Path file = storageFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return StreamUtils.copyToByteArray(resource.getInputStream());
            }
            else {
                throw new MediaException(
                        "Could not read file: " + fileName);
            }
        }
        catch (IOException exception) {
            throw new RuntimeException("Could not read file: " + fileName, exception);
        }
    }

    @Override
    public String uploadToCloud(MultipartFile file) {
        if (file.isEmpty()) {
            throw new MediaException("Failed to store empty file !");
        }
        if(!isImageFile(file)) {
            throw new MediaException("You can only upload image file !");
        }
        float fileSizeInMegabytes = file.getSize() / 1_000_000.0f;
        if(fileSizeInMegabytes > 5.0f) {
            throw new MediaException("File must be <= 5Mb !");
        }
        if (file.getOriginalFilename() == null) {
            throw new MediaException("File name is blank !");
        }
        try {
            return (String) cloudinary.uploader().upload(
                    file.getBytes(), ObjectUtils.emptyMap()
            ).get("url");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
