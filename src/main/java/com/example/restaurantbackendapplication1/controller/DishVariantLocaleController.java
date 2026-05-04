package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
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
import com.example.restaurantbackendapplication1.service.MenuService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{variant-id}/locales")
public class DishVariantLocaleController {

    private final DishVariantLocaleService dishVariantLocaleService;
    private final DishVariantService dishVariantService;
    private final DishService dishService;
    private final MenuCategoryService menuCategoryService;
    private final MenuService menuService;
    private final LocaleService localeService;

    public DishVariantLocaleController(DishVariantLocaleService dishVariantLocaleService,
                                       DishVariantService dishVariantService,
                                       DishService dishService,
                                       MenuCategoryService menuCategoryService,
                                       MenuService menuService,
                                       LocaleService localeService) {
        this.dishVariantLocaleService = dishVariantLocaleService;
        this.dishVariantService = dishVariantService;
        this.dishService = dishService;
        this.menuCategoryService = menuCategoryService;
        this.menuService = menuService;
        this.localeService = localeService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable("dish-id") Long dishId,
            @PathVariable("variant-id") Long variantId,
            @Valid @RequestBody CreateDishVariantLocaleRequest request) {
        menuService.getEntityById(menuId);
        menuCategoryService.getEntityById(menuId, menuCategoryId);
        dishService.getEntityById(menuCategoryId, dishId);
        DishVariantEntity dishVariantEntity = dishVariantService.getEntityById(dishId, variantId);
        LocaleEntity localeEntity = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dishVariantLocaleService.create(dishVariantEntity, localeEntity, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable("dish-id") Long dishId,
            @PathVariable("variant-id") Long variantId,
            @PathVariable Long id) {
        menuService.getEntityById(menuId);
        menuCategoryService.getEntityById(menuId, menuCategoryId);
        dishService.getEntityById(menuCategoryId, dishId);
        return ResponseEntity.ok(dishVariantLocaleService.getById(variantId, id));
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable("dish-id") Long dishId,
            @PathVariable("variant-id") Long variantId,
            @Valid @ParameterObject PaginatedRequest request) {
        menuService.getEntityById(menuId);
        menuCategoryService.getEntityById(menuId, menuCategoryId);
        dishService.getEntityById(menuCategoryId, dishId);
        return ResponseEntity.ok(dishVariantLocaleService.getAll(variantId, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable("dish-id") Long dishId,
            @PathVariable("variant-id") Long variantId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateDishVariantLocaleRequest request) {
        menuService.getEntityById(menuId);
        menuCategoryService.getEntityById(menuId, menuCategoryId);
        dishService.getEntityById(menuCategoryId, dishId);
        DishVariantLocaleEntity entity = dishVariantLocaleService.getEntityById(variantId, id);
        LocaleEntity localeEntity = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.ok(dishVariantLocaleService.update(entity, localeEntity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable("dish-id") Long dishId,
            @PathVariable("variant-id") Long variantId,
            @PathVariable Long id) {
        menuService.getEntityById(menuId);
        menuCategoryService.getEntityById(menuId, menuCategoryId);
        dishService.getEntityById(menuCategoryId, dishId);
        DishVariantLocaleEntity entity = dishVariantLocaleService.getEntityById(variantId, id);
        return ResponseEntity.ok(dishVariantLocaleService.delete(entity));
    }
}
