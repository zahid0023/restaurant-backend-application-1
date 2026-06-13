package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.dto.request.dishlocale.CreateDishLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.dishlocale.UpdateDishLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.DishEntity;
import com.example.restaurantbackendapplication1.model.entity.DishLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.service.DishLocaleService;
import com.example.restaurantbackendapplication1.service.DishService;
import com.example.restaurantbackendapplication1.service.LocaleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dishes/{dish-id}/locales")
public class DishLocaleController {

    private final DishLocaleService dishLocaleService;
    private final DishService dishService;
    private final LocaleService localeService;

    public DishLocaleController(DishLocaleService dishLocaleService,
                                DishService dishService,
                                LocaleService localeService) {
        this.dishLocaleService = dishLocaleService;
        this.dishService = dishService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable("dish-id") Long dishId,
            @Valid @RequestBody CreateDishLocaleRequest request) {
        DishEntity dishEntity = dishService.getEntityById(dishId);
        LocaleEntity localeEntity = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dishLocaleService.create(dishEntity, localeEntity, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("dish-id") Long dishId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateDishLocaleRequest request) {
        DishLocaleEntity entity = dishLocaleService.getEntityById(dishId, id);
        return ResponseEntity.ok(dishLocaleService.update(entity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("dish-id") Long dishId,
            @PathVariable Long id) {
        DishLocaleEntity entity = dishLocaleService.getEntityById(dishId, id);
        return ResponseEntity.ok(dishLocaleService.delete(entity));
    }
}
