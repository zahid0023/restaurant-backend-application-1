package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.dto.request.dishrecipeingredient.CreateDishRecipeIngredientRequest;
import com.example.restaurantbackendapplication1.dto.request.dishrecipeingredient.UpdateDishRecipeIngredientRequest;
import com.example.restaurantbackendapplication1.model.entity.DishRecipeEntity;
import com.example.restaurantbackendapplication1.model.entity.DishRecipeIngredientEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.service.DishRecipeIngredientService;
import com.example.restaurantbackendapplication1.service.DishRecipeService;
import com.example.restaurantbackendapplication1.service.DishService;
import com.example.restaurantbackendapplication1.service.DishVariantService;
import com.example.restaurantbackendapplication1.service.ItemService;
import com.example.restaurantbackendapplication1.service.MenuCategoryService;
import com.example.restaurantbackendapplication1.service.MenuService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants/{variant-id}/recipes/{recipe-id}/ingredients")
public class DishRecipeIngredientController {

    private final DishRecipeIngredientService dishRecipeIngredientService;
    private final DishRecipeService dishRecipeService;
    private final DishVariantService dishVariantService;
    private final DishService dishService;
    private final MenuCategoryService menuCategoryService;
    private final MenuService menuService;
    private final ItemService itemService;

    public DishRecipeIngredientController(DishRecipeIngredientService dishRecipeIngredientService,
                                          DishRecipeService dishRecipeService,
                                          DishVariantService dishVariantService,
                                          DishService dishService,
                                          MenuCategoryService menuCategoryService,
                                          MenuService menuService,
                                          ItemService itemService) {
        this.dishRecipeIngredientService = dishRecipeIngredientService;
        this.dishRecipeService = dishRecipeService;
        this.dishVariantService = dishVariantService;
        this.dishService = dishService;
        this.menuCategoryService = menuCategoryService;
        this.menuService = menuService;
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable("dish-id") Long dishId,
            @PathVariable("variant-id") Long variantId,
            @PathVariable("recipe-id") Long recipeId,
            @Valid @RequestBody CreateDishRecipeIngredientRequest request) {
        menuService.getEntityById(menuId);
        menuCategoryService.getEntityById(menuId, menuCategoryId);
        dishService.getEntityById(menuCategoryId, dishId);
        dishVariantService.getEntityById(dishId, variantId);
        DishRecipeEntity dishRecipeEntity = dishRecipeService.getEntityById(variantId, recipeId);
        ItemEntity itemEntity = itemService.getEntityById(request.getItemId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dishRecipeIngredientService.create(request, dishRecipeEntity, itemEntity));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable("dish-id") Long dishId,
            @PathVariable("variant-id") Long variantId,
            @PathVariable("recipe-id") Long recipeId,
            @PathVariable Long id) {
        menuService.getEntityById(menuId);
        menuCategoryService.getEntityById(menuId, menuCategoryId);
        dishService.getEntityById(menuCategoryId, dishId);
        dishVariantService.getEntityById(dishId, variantId);
        return ResponseEntity.ok(dishRecipeIngredientService.getById(recipeId, id));
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable("dish-id") Long dishId,
            @PathVariable("variant-id") Long variantId,
            @PathVariable("recipe-id") Long recipeId,
            @Valid @ParameterObject PaginatedRequest request) {
        menuService.getEntityById(menuId);
        menuCategoryService.getEntityById(menuId, menuCategoryId);
        dishService.getEntityById(menuCategoryId, dishId);
        dishVariantService.getEntityById(dishId, variantId);
        return ResponseEntity.ok(dishRecipeIngredientService.getAll(recipeId, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable("dish-id") Long dishId,
            @PathVariable("variant-id") Long variantId,
            @PathVariable("recipe-id") Long recipeId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateDishRecipeIngredientRequest request) {
        menuService.getEntityById(menuId);
        menuCategoryService.getEntityById(menuId, menuCategoryId);
        dishService.getEntityById(menuCategoryId, dishId);
        dishVariantService.getEntityById(dishId, variantId);
        DishRecipeIngredientEntity entity = dishRecipeIngredientService.getEntityById(recipeId, id);
        ItemEntity itemEntity = itemService.getEntityById(request.getItemId());
        return ResponseEntity.ok(dishRecipeIngredientService.update(entity, itemEntity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable("dish-id") Long dishId,
            @PathVariable("variant-id") Long variantId,
            @PathVariable("recipe-id") Long recipeId,
            @PathVariable Long id) {
        menuService.getEntityById(menuId);
        menuCategoryService.getEntityById(menuId, menuCategoryId);
        dishService.getEntityById(menuCategoryId, dishId);
        dishVariantService.getEntityById(dishId, variantId);
        DishRecipeIngredientEntity entity = dishRecipeIngredientService.getEntityById(recipeId, id);
        return ResponseEntity.ok(dishRecipeIngredientService.delete(entity));
    }
}
