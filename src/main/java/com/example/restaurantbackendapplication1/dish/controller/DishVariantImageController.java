package com.example.restaurantbackendapplication1.dish.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.dish.dto.request.dishvariantimage.UpdateDishVariantImageRequest;
import com.example.restaurantbackendapplication1.dish.model.entity.DishVariantEntity;
import com.example.restaurantbackendapplication1.dish.model.entity.DishVariantImageEntity;
import com.example.restaurantbackendapplication1.dish.service.DishVariantImageService;
import com.example.restaurantbackendapplication1.dish.service.DishVariantService;
import com.example.restaurantbackendapplication1.imagehosting.dto.request.ImageMetaRequest;
import com.example.restaurantbackendapplication1.imagehosting.dto.request.ImageRequest;
import com.example.restaurantbackendapplication1.imagehosting.model.entity.RestaurantImageHostingConfigEntity;
import com.example.restaurantbackendapplication1.imagehosting.service.ImageUploadService;
import com.example.restaurantbackendapplication1.imagehosting.service.RestaurantImageHostingConfigService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dishes/{dish-id}/variants/{variant-id}/images")
public class DishVariantImageController {

    private final DishVariantImageService dishVariantImageService;
    private final DishVariantService dishVariantService;
    private final RestaurantImageHostingConfigService imageHostingConfigService;
    private final ImageUploadService imageUploadService;

    public DishVariantImageController(DishVariantImageService dishVariantImageService,
                                      DishVariantService dishVariantService,
                                      RestaurantImageHostingConfigService imageHostingConfigService,
                                      ImageUploadService imageUploadService) {
        this.dishVariantImageService = dishVariantImageService;
        this.dishVariantService = dishVariantService;
        this.imageHostingConfigService = imageHostingConfigService;
        this.imageUploadService = imageUploadService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(
            @PathVariable("dish-id") Long dishId,
            @PathVariable("variant-id") Long variantId,
            @RequestPart("meta") List<ImageMetaRequest> imageMetaRequests,
            @RequestParam("config_id") Long configId,
            @RequestPart("images") List<MultipartFile> images) {
        DishVariantEntity dishVariantEntity = dishVariantService.getEntityById(dishId, variantId);
        RestaurantImageHostingConfigEntity config = imageHostingConfigService.getEntityById(configId);
        List<ImageRequest> imageRequests = imageUploadService.uploadAll(
                images, imageMetaRequests, config.getProvider(), config.getConfig()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dishVariantImageService.createAll(imageRequests, config, dishVariantEntity));
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PathVariable("dish-id") Long dishId,
            @PathVariable("variant-id") Long variantId,
            @Valid @ParameterObject PaginatedRequest request) {
        return ResponseEntity.ok(dishVariantImageService.getAll(variantId, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable("dish-id") Long dishId,
            @PathVariable("variant-id") Long variantId,
            @PathVariable Long id) {
        return ResponseEntity.ok(dishVariantImageService.getById(variantId, id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("dish-id") Long dishId,
            @PathVariable("variant-id") Long variantId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateDishVariantImageRequest request) {
        DishVariantImageEntity entity = dishVariantImageService.getEntityById(variantId, id);
        return ResponseEntity.ok(dishVariantImageService.update(entity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("dish-id") Long dishId,
            @PathVariable("variant-id") Long variantId,
            @PathVariable Long id) {
        DishVariantImageEntity entity = dishVariantImageService.getEntityById(variantId, id);
        return ResponseEntity.ok(dishVariantImageService.delete(entity));
    }
}
