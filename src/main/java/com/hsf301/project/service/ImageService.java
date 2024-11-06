package com.hsf301.project.service;

import com.hsf301.project.exception.FileUploadException;
import com.hsf301.project.exception.ImageLoadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ImageService {

    @Value("${ImageController.saveLocation}")
    private String UPLOAD_DIR;

    // Lưu một ảnh duy nhất và trả về URL
    public String saveSingleImageAndGetUrl(MultipartFile file) throws IOException, NoSuchAlgorithmException {
        String fileName = saveImage(file);
        return getImageUrl(fileName);
    }

    // Lưu nhiều ảnh và trả về danh sách URL
    public List<String> saveMultipleImagesAndGetUrls(MultipartFile[] files) throws IOException, NoSuchAlgorithmException {
        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = saveImage(file);
            imageUrls.add(getImageUrl(fileName));
        }
        return imageUrls;
    }

    // Lưu ảnh và trả về tên file
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
    
        return newFileName;
    }

    // Tạo URL truy cập ảnh
    public String getImageUrl(String fileName) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/images/uploads/")
                .path(fileName)
                .toUriString();
    }

    // Lấy ảnh dưới dạng `ResponseEntity` cho HTTP response
    public ResponseEntity<Object> getImageAsResponseEntity(String filename) {
        Map<String, Object> data = getImageData(filename, false);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType((String) data.get("MediaType")))
                .body(data.get("image"));
    }

    // Lấy dữ liệu ảnh từ tên tệp
    private Map<String, Object> getImageData(String filename, Boolean includeExtension) {
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

            if (includeExtension) {
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
