package com.hsf301.project.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hsf301.project.exception.FileUploadException;
import com.hsf301.project.exception.ImageLoadException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import java.util.Map;
import java.util.HashMap;
import java.util.UUID;


import java.security.NoSuchAlgorithmException;

@Service
public class ImageService {
    @Value("${ImageController.saveLocation}")
    private String UPLOAD_DIR;

    public String[] saveImages(MultipartFile[] files) throws IOException, NoSuchAlgorithmException {
        String[] filePaths = new String[files.length];

        for (int i = 0; i < files.length; i++) {
            filePaths[i] = saveImage(files[i]);
        }

        return filePaths;
    }

    private String saveImage(MultipartFile file) throws IOException, NoSuchAlgorithmException {
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

        String newFileName = UUID.randomUUID().toString() + "@" + baseName + extension;

        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        File destinationFile = new File(uploadDir, newFileName);
        file.transferTo(destinationFile);

        return destinationFile.getAbsolutePath();
    }

    public byte[] getImage(String filename) {
        File file = new File(UPLOAD_DIR + filename);
        if (!file.exists()) {
            throw new ImageLoadException(filename + " does not exist");
        }
    
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new ImageLoadException("Failed to load image data for " + filename, e);
        }
    }

    public String getImageUrl(String image) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/images/id/")
                .path(image)
                .toUriString();
    }

    public Map<String, Object> getImage(String filename, Boolean fileExtension) {
        File file = new File(UPLOAD_DIR + filename);
        Map<String, Object> data = new HashMap<>();

        if (!file.exists()) {
            throw new ImageLoadException(filename + " does not exist");
        }

        try {
            String mediaType = Files.probeContentType(file.toPath());
            data.put("MediaType", mediaType);
            byte[] imageData = Files.readAllBytes(file.toPath());
            data.put("image", imageData);

            if (fileExtension) {
                String extension = getFileExtension(filename);
                data.put("extension", extension);
            }

            return data;
        } catch (IOException e) {
            throw new ImageLoadException("Failed to load image data for " + filename, e);
        }
    }

    private String getFileExtension(String filename) {
        int lastIndex = filename.lastIndexOf('.');
        return (lastIndex > 0) ? filename.substring(lastIndex + 1) : "";
    }
}
