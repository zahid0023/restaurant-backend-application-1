package com.example.restaurantbackendapplication1.menu.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.menu.dto.request.menucategorydish.CreateMenuCategoryDishRequest;
import com.example.restaurantbackendapplication1.dish.model.entity.DishEntity;
import com.example.restaurantbackendapplication1.menu.model.entity.MenuCategoryDishEntity;
import com.example.restaurantbackendapplication1.menu.model.entity.MenuCategoryEntity;
import com.example.restaurantbackendapplication1.dish.service.DishService;
import com.example.restaurantbackendapplication1.menu.service.MenuCategoryDishService;
import com.example.restaurantbackendapplication1.menu.service.MenuCategoryService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/menu-categories/{menu-category-id}/dishes")
public class MenuCategoryDishController {

    private final MenuCategoryDishService menuCategoryDishService;
    private final MenuCategoryService menuCategoryService;
    private final DishService dishService;

    public MenuCategoryDishController(MenuCategoryDishService menuCategoryDishService,
                                      MenuCategoryService menuCategoryService,
                                      DishService dishService) {
        this.menuCategoryDishService = menuCategoryDishService;
        this.menuCategoryService = menuCategoryService;
        this.dishService = dishService;
    }

    @PostMapping("/assign")
    public ResponseEntity<?> assign(
            @PathVariable("menu-category-id") Long menuCategoryId,
            @Valid @RequestBody CreateMenuCategoryDishRequest request) {
        MenuCategoryEntity menuCategory = menuCategoryService.getEntityById(menuCategoryId);
        DishEntity dish = dishService.getEntityById(request.getDishId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(menuCategoryDishService.assign(menuCategory, dish));
    }

    @DeleteMapping("/{dish-id}/unassign")
    public ResponseEntity<?> unassign(
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable("dish-id") Long dishId) {
        MenuCategoryDishEntity entity = menuCategoryDishService.getEntityById(menuCategoryId, dishId);
        return ResponseEntity.ok(menuCategoryDishService.unassign(entity));
    }
}
