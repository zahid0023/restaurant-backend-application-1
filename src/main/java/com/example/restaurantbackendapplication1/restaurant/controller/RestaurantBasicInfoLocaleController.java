package com.example.restaurantbackendapplication1.restaurant.controller;

import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.locale.service.LocaleService;
import com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantbasicinfolocale.CreateRestaurantBasicInfoLocaleRequest;
import com.example.restaurantbackendapplication1.restaurant.dto.request.restaurantbasicinfolocale.UpdateRestaurantBasicInfoLocaleRequest;
import com.example.restaurantbackendapplication1.restaurant.model.entity.RestaurantBasicInfoEntity;
import com.example.restaurantbackendapplication1.restaurant.model.entity.RestaurantBasicInfoLocaleEntity;
import com.example.restaurantbackendapplication1.restaurant.service.RestaurantBasicInfoLocaleService;
import com.example.restaurantbackendapplication1.restaurant.service.RestaurantBasicInfoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/restaurant-basic-info/{restaurant-basic-info-id}/locales")
public class RestaurantBasicInfoLocaleController {

    private final RestaurantBasicInfoLocaleService restaurantBasicInfoLocaleService;
    private final RestaurantBasicInfoService restaurantBasicInfoService;
    private final LocaleService localeService;

    public RestaurantBasicInfoLocaleController(
            RestaurantBasicInfoLocaleService restaurantBasicInfoLocaleService,
            RestaurantBasicInfoService restaurantBasicInfoService,
            LocaleService localeService) {
        this.restaurantBasicInfoLocaleService = restaurantBasicInfoLocaleService;
        this.restaurantBasicInfoService = restaurantBasicInfoService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable("restaurant-basic-info-id") Long restaurantBasicInfoId,
            @Valid @RequestBody CreateRestaurantBasicInfoLocaleRequest request) {
        RestaurantBasicInfoEntity restaurantBasicInfoEntity = restaurantBasicInfoService.getEntityById(restaurantBasicInfoId);
        LocaleEntity localeEntity = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(restaurantBasicInfoLocaleService.create(restaurantBasicInfoEntity, localeEntity, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("restaurant-basic-info-id") Long restaurantBasicInfoId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateRestaurantBasicInfoLocaleRequest request) {
        RestaurantBasicInfoLocaleEntity entity = restaurantBasicInfoLocaleService.getEntityById(restaurantBasicInfoId, id);
        return ResponseEntity.ok(restaurantBasicInfoLocaleService.update(entity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("restaurant-basic-info-id") Long restaurantBasicInfoId,
            @PathVariable Long id) {
        RestaurantBasicInfoLocaleEntity entity = restaurantBasicInfoLocaleService.getEntityById(restaurantBasicInfoId, id);
        return ResponseEntity.ok(restaurantBasicInfoLocaleService.delete(entity));
    }
}
