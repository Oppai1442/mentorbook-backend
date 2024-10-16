package com.hsf301.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import java.security.NoSuchAlgorithmException;

import com.hsf301.project.utils.Utils;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final Utils utils = new Utils(); // Khai báo đối tượng Utils

    private static final String UPLOAD_DIR = "C:\\Users\\daeor\\Documents\\Project\\Image";

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file uploaded.");
        }

        try {
            // Lấy tên gốc và phần mở rộng của tệp
            String originalFilename = file.getOriginalFilename();

            if (originalFilename == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File name is missing.");
            }

            String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            String baseName = originalFilename.substring(0, originalFilename.lastIndexOf('.'));
            
            long currentTime = System.currentTimeMillis();

            String hash = utils.hashGenerate(baseName + currentTime);
            
            String newFileName = hash + currentTime + "_@" + baseName + extension;

            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            File destinationFile = new File(uploadDir, newFileName);
            file.transferTo(destinationFile);

            return ResponseEntity.ok("File uploaded successfully: " + destinationFile.getAbsolutePath());
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file.");
        }
    }

    @GetMapping("/uploads/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) {
        File file = new File(UPLOAD_DIR + filename);
        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        try {
            byte[] imageData = java.nio.file.Files.readAllBytes(file.toPath());
            return ResponseEntity.ok().contentType(org.springframework.http.MediaType.IMAGE_JPEG).body(imageData);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}