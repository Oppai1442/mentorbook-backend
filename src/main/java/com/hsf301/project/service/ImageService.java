package com.hsf301.project.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hsf301.project.exception.FileUploadException;
import com.hsf301.project.utils.Utils;

import java.io.File;
import java.io.IOException;

import java.security.NoSuchAlgorithmException;

@Service
public class ImageService {

    private final Utils utils = new Utils();

    @Value("${ImageController.saveLocation}")
    private String UPLOAD_DIR;

    public String saveImage(MultipartFile file) throws IOException, NoSuchAlgorithmException {
        String originalFilename = file.getOriginalFilename();

        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new FileUploadException("File name is missing.");
        }

        String extension = "";
        int lastDotIndex = originalFilename.lastIndexOf('.');
        if (lastDotIndex >= 0) {
            extension = originalFilename.substring(lastDotIndex);
        }

        String baseName = originalFilename.substring(0, lastDotIndex >= 0 ? lastDotIndex : originalFilename.length());
        long currentTime = System.currentTimeMillis();

        String hash = utils.hashGenerate(baseName + currentTime);
        String newFileName = hash + currentTime + "_@" + baseName + extension;

        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        File destinationFile = new File(uploadDir, newFileName);
        file.transferTo(destinationFile);

        return destinationFile.getAbsolutePath();
    }
}
