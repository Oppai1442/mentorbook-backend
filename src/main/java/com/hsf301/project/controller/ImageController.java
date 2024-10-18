package com.hsf301.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.io.IOException;

import java.security.NoSuchAlgorithmException;

import com.hsf301.project.exception.FileUploadException;
import com.hsf301.project.service.ImageService;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Value("${ImageController.saveLocation}")
    private String UPLOAD_DIR;

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile[] files) {
        if (files.length == 0) {
            throw new FileUploadException("No files uploaded.");
        }

        StringBuilder responseMessage = new StringBuilder();

        for (MultipartFile file : files) {
            try {
                String filePath = imageService.saveImage(file);
                responseMessage.append("File uploaded successfully: ").append(filePath).append("\n");
            } catch (IOException | NoSuchAlgorithmException e) {
                e.printStackTrace();
                responseMessage.append("Failed to upload file: ").append(file.getOriginalFilename()).append("\n");
            }
        }

        return ResponseEntity.ok(responseMessage.toString());
    }

    @GetMapping("/uploads/{filename}")
    public ResponseEntity<Object> getImage(@PathVariable String filename) {
        File file = new File(UPLOAD_DIR + filename);
        System.out.println(UPLOAD_DIR + filename);
        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found: " + filename);
        }

        try {
            byte[] imageData = java.nio.file.Files.readAllBytes(file.toPath());
            String contentType = Files.probeContentType(file.toPath());
            return ResponseEntity.ok()
                                .contentType(MediaType.parseMediaType(contentType))
                                .body(imageData);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}