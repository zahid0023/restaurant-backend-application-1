package com.example.restaurantbackendapplication1.restaurant.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.imagehosting.dto.request.ImageMetaRequest;
import com.example.restaurantbackendapplication1.imagehosting.dto.request.ImageRequest;
import com.example.restaurantbackendapplication1.imagehosting.model.entity.RestaurantImageHostingConfigEntity;
import com.example.restaurantbackendapplication1.imagehosting.service.ImageUploadService;
import com.example.restaurantbackendapplication1.imagehosting.service.RestaurantImageHostingConfigService;
import com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantimage.UpdateRestaurantImageRequest;
import com.example.restaurantbackendapplication1.restaurant.model.entity.RestaurantImageEntity;
import com.example.restaurantbackendapplication1.restaurant.service.RestaurantImageService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurant-images")
public class RestaurantImageController {

    private final RestaurantImageService restaurantImageService;
    private final RestaurantImageHostingConfigService imageHostingConfigService;
    private final ImageUploadService imageUploadService;

    public RestaurantImageController(RestaurantImageService restaurantImageService,
                                     RestaurantImageHostingConfigService imageHostingConfigService,
                                     ImageUploadService imageUploadService) {
        this.restaurantImageService = restaurantImageService;
        this.imageHostingConfigService = imageHostingConfigService;
        this.imageUploadService = imageUploadService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(
            @RequestPart("meta") List<ImageMetaRequest> imageMetaRequests,
            @RequestParam("config_id") Long configId,
            @RequestPart("images") List<MultipartFile> images) {
        RestaurantImageHostingConfigEntity config = imageHostingConfigService.getEntityById(configId);
        List<ImageRequest> imageRequests = imageUploadService.uploadAll(
                images, imageMetaRequests, config.getProvider(), config.getConfig()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(restaurantImageService.createAll(imageRequests, config));
    }

    @GetMapping
    public ResponseEntity<?> getAll(@Valid @ParameterObject PaginatedRequest request) {
        return ResponseEntity.ok(restaurantImageService.getAll(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantImageService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody UpdateRestaurantImageRequest request) {
        RestaurantImageEntity entity = restaurantImageService.getEntityById(id);
        return ResponseEntity.ok(restaurantImageService.update(entity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        RestaurantImageEntity entity = restaurantImageService.getEntityById(id);
        return ResponseEntity.ok(restaurantImageService.delete(entity));
    }
}
