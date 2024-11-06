package com.hsf301.project.controller;

import com.hsf301.project.exception.FileUploadException;
import com.hsf301.project.model.response.ApiResponse;
import com.hsf301.project.model.response.ImageUrl;
import com.hsf301.project.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    // Xử lý upload một ảnh duy nhất
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<ImageUrl>> uploadSingleImage(@RequestParam("image") MultipartFile file) {
        if (file.isEmpty()) {
            throw new FileUploadException("No file uploaded.");
        }
    
        try {
            String imageUrl = imageService.saveSingleImageAndGetUrl(file);
            ImageUrl imageUrlResponse = new ImageUrl(imageUrl);
            return ResponseEntity.ok(new ApiResponse<ImageUrl>(imageUrlResponse));
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ApiResponse<>(null));
        }
    }
    

    // Xử lý upload nhiều ảnh
    @PostMapping("/uploads")
    public ResponseEntity<ApiResponse<List<String>>> uploadMultipleImages(@RequestParam("image") MultipartFile[] files) {
        if (files.length == 0) {
            throw new FileUploadException("No files uploaded.");
        }

        try {
            List<String> imageUrls = imageService.saveMultipleImagesAndGetUrls(files);
            return ResponseEntity.ok(new ApiResponse<>(imageUrls));
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ApiResponse<>(null));
        }
    }

    // Lấy ảnh theo tên tệp
    @GetMapping("/uploads/{filename}")
    public ResponseEntity<Object> getImage(@PathVariable String filename) {
        return imageService.getImageAsResponseEntity(filename);
    }
}
