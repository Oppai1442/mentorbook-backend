package com.hsf301.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

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

        try {
            String[] filePaths = imageService.saveImages(files);
            for (String filePath : filePaths) {
                responseMessage.append("File uploaded successfully: ").append(filePath).append("\n");
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            responseMessage.append("Failed to upload files.\n");
        }

        return ResponseEntity.ok(responseMessage.toString());
    }

    @GetMapping("/uploads/{filename}")
    public ResponseEntity<Object> getImage(@PathVariable String filename) {
        Map<String, Object> data = imageService.getImage(filename, false);

        return ResponseEntity.ok()
                                .contentType(MediaType.parseMediaType((String) data.get("MediaType")))
                                .body(data.get("image"));
    }

    @GetMapping("/id/{fileName:.+}")
    public ResponseEntity<Object> getImageUrl(@PathVariable String fileName) {
        Map<String, Object> data = imageService.getImage(fileName, false);

        return ResponseEntity.ok()
                                .contentType(MediaType.parseMediaType((String) data.get("MediaType")))
                                .body(data.get("image"));
    }
}