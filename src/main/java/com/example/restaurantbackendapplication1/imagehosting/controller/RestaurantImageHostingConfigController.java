package com.example.restaurantbackendapplication1.imagehosting.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.imagehosting.dto.request.CreateRestaurantImageHostingConfigRequest;
import com.example.restaurantbackendapplication1.imagehosting.dto.response.ImageHostingProviderResponse;
import com.example.restaurantbackendapplication1.imagehosting.enums.ImageHostingProvider;
import com.example.restaurantbackendapplication1.imagehosting.service.RestaurantImageHostingConfigService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurant-image-hosting-configs")
public class RestaurantImageHostingConfigController {

    private final RestaurantImageHostingConfigService restaurantImageHostingConfigService;

    public RestaurantImageHostingConfigController(
            RestaurantImageHostingConfigService restaurantImageHostingConfigService) {
        this.restaurantImageHostingConfigService = restaurantImageHostingConfigService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateRestaurantImageHostingConfigRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(restaurantImageHostingConfigService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantImageHostingConfigService.getById(id));
    }

    @GetMapping
    public ResponseEntity<?> getAll(@Valid @ParameterObject PaginatedRequest request) {
        return ResponseEntity.ok(restaurantImageHostingConfigService.getAll(request));
    }

    @GetMapping("/providers")
    public ResponseEntity<?> getProviders() {
        List<ImageHostingProviderResponse> providers = Arrays.stream(ImageHostingProvider.values())
                .map(ImageHostingProviderResponse::from)
                .toList();
        return ResponseEntity.ok(providers);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantImageHostingConfigService.delete(id));
    }
}
