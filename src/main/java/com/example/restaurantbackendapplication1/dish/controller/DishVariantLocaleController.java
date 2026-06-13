package com.example.restaurantbackendapplication1.dish.controller;

import com.example.restaurantbackendapplication1.dish.dto.request.dishvariantlocale.CreateDishVariantLocaleRequest;
import com.example.restaurantbackendapplication1.dish.dto.request.dishvariantlocale.UpdateDishVariantLocaleRequest;
import com.example.restaurantbackendapplication1.dish.model.entity.DishVariantEntity;
import com.example.restaurantbackendapplication1.dish.model.entity.DishVariantLocaleEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.dish.service.DishVariantLocaleService;
import com.example.restaurantbackendapplication1.dish.service.DishVariantService;
import com.example.restaurantbackendapplication1.locale.service.LocaleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dishes/{dish-id}/variants/{variant-id}/locales")
public class DishVariantLocaleController {

    private final DishVariantLocaleService dishVariantLocaleService;
    private final DishVariantService dishVariantService;
    private final LocaleService localeService;

    public DishVariantLocaleController(DishVariantLocaleService dishVariantLocaleService,
                                        DishVariantService dishVariantService,
                                        LocaleService localeService) {
        this.dishVariantLocaleService = dishVariantLocaleService;
        this.dishVariantService = dishVariantService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable("dish-id") Long dishId,
            @PathVariable("variant-id") Long variantId,
            @Valid @RequestBody CreateDishVariantLocaleRequest request) {
        DishVariantEntity dishVariantEntity = dishVariantService.getEntityById(dishId, variantId);
        LocaleEntity localeEntity = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dishVariantLocaleService.create(dishVariantEntity, localeEntity, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("dish-id") Long dishId,
            @PathVariable("variant-id") Long variantId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateDishVariantLocaleRequest request) {
        DishVariantLocaleEntity entity = dishVariantLocaleService.getEntityById(variantId, id);
        return ResponseEntity.ok(dishVariantLocaleService.update(entity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("dish-id") Long dishId,
            @PathVariable("variant-id") Long variantId,
            @PathVariable Long id) {
        DishVariantLocaleEntity entity = dishVariantLocaleService.getEntityById(variantId, id);
        return ResponseEntity.ok(dishVariantLocaleService.delete(entity));
    }
}
