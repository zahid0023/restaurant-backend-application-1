package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.dto.request.dishrecipe.CreateDishRecipeRequest;
import com.example.restaurantbackendapplication1.dto.request.dishrecipe.UpdateDishRecipeRequest;
import com.example.restaurantbackendapplication1.dto.request.dishrecipeingredient.DishRecipeIngredientRequest;
import com.example.restaurantbackendapplication1.model.entity.DishRecipeEntity;
import com.example.restaurantbackendapplication1.model.entity.DishVariantEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitEntity;
import com.example.restaurantbackendapplication1.service.DishRecipeService;
import com.example.restaurantbackendapplication1.service.DishService;
import com.example.restaurantbackendapplication1.service.DishVariantService;
import com.example.restaurantbackendapplication1.service.ItemService;
import com.example.restaurantbackendapplication1.service.MenuCategoryService;
import com.example.restaurantbackendapplication1.service.UnitService;
import com.example.restaurantbackendapplication1.utils.ItemUtils;
import com.example.restaurantbackendapplication1.utils.UnitUtils;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{variant-id}/recipes")
public class DishRecipeController {

    private final DishRecipeService dishRecipeService;
    private final DishVariantService dishVariantService;
    private final DishService dishService;
    private final MenuCategoryService menuCategoryService;
    private final ItemService itemService;
    private final UnitService unitService;

    public DishRecipeController(DishRecipeService dishRecipeService,
                                DishVariantService dishVariantService,
                                DishService dishService,
                                MenuCategoryService menuCategoryService,
                                ItemService itemService,
                                UnitService unitService) {
        this.dishRecipeService = dishRecipeService;
        this.dishVariantService = dishVariantService;
        this.dishService = dishService;
        this.menuCategoryService = menuCategoryService;
        this.itemService = itemService;
        this.unitService = unitService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable("dish-id") Long dishId,
            @PathVariable("variant-id") Long variantId,
            @Valid @RequestBody CreateDishRecipeRequest request) {
        menuCategoryService.getEntityById(menuCategoryId);
        DishVariantEntity dishVariantEntity = dishVariantService.getEntityById(dishId, variantId);
        Map<Long, ItemEntity> itemEntityMap = ItemUtils.resolveItemMap(
                request.getIngredients(), DishRecipeIngredientRequest::getItemId, itemService);
        Map<Long, UnitEntity> unitEntityMap = UnitUtils.resolveUnitMap(
                request.getIngredients(), DishRecipeIngredientRequest::getUnitId, unitService);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dishRecipeService.create(request, dishVariantEntity, itemEntityMap, unitEntityMap));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable("dish-id") Long dishId,
            @PathVariable("variant-id") Long variantId,
            @PathVariable Long id) {
        menuCategoryService.getEntityById(menuCategoryId);
        return ResponseEntity.ok(dishRecipeService.getById(variantId, id));
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable("dish-id") Long dishId,
            @PathVariable("variant-id") Long variantId,
            @Valid @ParameterObject PaginatedRequest request) {
        menuCategoryService.getEntityById(menuCategoryId);
        return ResponseEntity.ok(dishRecipeService.getAll(variantId, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable("dish-id") Long dishId,
            @PathVariable("variant-id") Long variantId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateDishRecipeRequest request) {
        menuCategoryService.getEntityById(menuCategoryId);
        DishRecipeEntity entity = dishRecipeService.getEntityById(variantId, id);
        return ResponseEntity.ok(dishRecipeService.update(entity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable("dish-id") Long dishId,
            @PathVariable("variant-id") Long variantId,
            @PathVariable Long id) {
        menuCategoryService.getEntityById(menuCategoryId);
        return ResponseEntity.ok(dishRecipeService.delete(variantId, id));
    }

}
