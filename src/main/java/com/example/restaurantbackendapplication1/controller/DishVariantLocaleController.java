package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.dto.request.dishvariantlocale.CreateDishVariantLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.dishvariantlocale.UpdateDishVariantLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.DishVariantEntity;
import com.example.restaurantbackendapplication1.model.entity.DishVariantLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.service.DishService;
import com.example.restaurantbackendapplication1.service.DishVariantLocaleService;
import com.example.restaurantbackendapplication1.service.DishVariantService;
import com.example.restaurantbackendapplication1.service.LocaleService;
import com.example.restaurantbackendapplication1.service.MenuCategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{variant-id}/locales")
public class DishVariantLocaleController {

    private final DishVariantLocaleService dishVariantLocaleService;
    private final DishVariantService dishVariantService;
    private final DishService dishService;
    private final MenuCategoryService menuCategoryService;
    private final LocaleService localeService;

    public DishVariantLocaleController(DishVariantLocaleService dishVariantLocaleService,
                                       DishVariantService dishVariantService,
                                       DishService dishService,
                                       MenuCategoryService menuCategoryService,
                                       LocaleService localeService) {
        this.dishVariantLocaleService = dishVariantLocaleService;
        this.dishVariantService = dishVariantService;
        this.dishService = dishService;
        this.menuCategoryService = menuCategoryService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable("dish-id") Long dishId,
            @PathVariable("variant-id") Long variantId,
            @Valid @RequestBody CreateDishVariantLocaleRequest request) {
        menuCategoryService.getEntityById(menuCategoryId);
        DishVariantEntity dishVariantEntity = dishVariantService.getEntityById(dishId, variantId);
        LocaleEntity localeEntity = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dishVariantLocaleService.create(dishVariantEntity, localeEntity, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable("dish-id") Long dishId,
            @PathVariable("variant-id") Long variantId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateDishVariantLocaleRequest request) {
        menuCategoryService.getEntityById(menuCategoryId);
        DishVariantLocaleEntity entity = dishVariantLocaleService.getEntityById(variantId, id);
        return ResponseEntity.ok(dishVariantLocaleService.update(entity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable("dish-id") Long dishId,
            @PathVariable("variant-id") Long variantId,
            @PathVariable Long id) {
        menuCategoryService.getEntityById(menuCategoryId);
        DishVariantLocaleEntity entity = dishVariantLocaleService.getEntityById(variantId, id);
        return ResponseEntity.ok(dishVariantLocaleService.delete(entity));
    }
}
