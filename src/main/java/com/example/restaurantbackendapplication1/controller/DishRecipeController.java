package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.dto.request.dishrecipe.CreateDishRecipeRequest;
import com.example.restaurantbackendapplication1.dto.request.dishrecipe.UpdateDishRecipeRequest;
import com.example.restaurantbackendapplication1.model.entity.DishRecipeEntity;
import com.example.restaurantbackendapplication1.model.entity.DishVariantEntity;
import com.example.restaurantbackendapplication1.service.DishRecipeService;
import com.example.restaurantbackendapplication1.service.DishService;
import com.example.restaurantbackendapplication1.service.DishVariantService;
import com.example.restaurantbackendapplication1.service.MenuCategoryService;
import com.example.restaurantbackendapplication1.service.MenuService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{variant-id}/recipes")
public class DishRecipeController {

    private final DishRecipeService dishRecipeService;
    private final DishVariantService dishVariantService;
    private final DishService dishService;
    private final MenuCategoryService menuCategoryService;
    private final MenuService menuService;

    public DishRecipeController(DishRecipeService dishRecipeService,
                                DishVariantService dishVariantService,
                                DishService dishService,
                                MenuCategoryService menuCategoryService,
                                MenuService menuService) {
        this.dishRecipeService = dishRecipeService;
        this.dishVariantService = dishVariantService;
        this.dishService = dishService;
        this.menuCategoryService = menuCategoryService;
        this.menuService = menuService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable("dish-id") Long dishId,
            @PathVariable("variant-id") Long variantId,
            @Valid @RequestBody CreateDishRecipeRequest request) {
        menuService.getEntityById(menuId);
        menuCategoryService.getEntityById(menuId, menuCategoryId);
        dishService.getEntityById(menuCategoryId, dishId);
        DishVariantEntity dishVariantEntity = dishVariantService.getEntityById(dishId, variantId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dishRecipeService.create(request, dishVariantEntity));
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
        return ResponseEntity.ok(dishRecipeService.getById(variantId, id));
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
        return ResponseEntity.ok(dishRecipeService.getAll(variantId, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable("dish-id") Long dishId,
            @PathVariable("variant-id") Long variantId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateDishRecipeRequest request) {
        menuService.getEntityById(menuId);
        menuCategoryService.getEntityById(menuId, menuCategoryId);
        dishService.getEntityById(menuCategoryId, dishId);
        DishRecipeEntity entity = dishRecipeService.getEntityById(variantId, id);
        return ResponseEntity.ok(dishRecipeService.update(entity, request));
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
        return ResponseEntity.ok(dishRecipeService.delete(variantId, id));
    }
}
