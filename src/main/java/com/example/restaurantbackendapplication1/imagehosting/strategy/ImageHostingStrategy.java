package com.example.restaurantbackendapplication1.imagehosting.strategy;

import com.example.restaurantbackendapplication1.imagehosting.dto.response.ImageUploadResponse;
import com.example.restaurantbackendapplication1.imagehosting.enums.ImageHostingProvider;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ImageHostingStrategy {
    ImageHostingProvider provider();

    ImageUploadResponse upload(MultipartFile file, Map<String, String> configs);

    void delete(String publicId, Map<String, String> config);
}
