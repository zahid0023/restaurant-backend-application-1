package com.example.restaurantbackendapplication1.menu.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.menu.service.MenuCategoryDishService;
import com.example.restaurantbackendapplication1.menu.service.MenuCategoryService;
import com.example.restaurantbackendapplication1.menu.service.MenuTypeService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/menus/public")
public class MenuController {
    private final MenuTypeService menuTypeService;
    private final MenuCategoryService menuCategoryService;
    private final MenuCategoryDishService menuCategoryDishService;

    public MenuController(MenuTypeService menuTypeService,
                          MenuCategoryService menuCategoryService,
                          MenuCategoryDishService menuCategoryDishService) {
        this.menuTypeService = menuTypeService;
        this.menuCategoryService = menuCategoryService;
        this.menuCategoryDishService = menuCategoryDishService;
    }

    @GetMapping("/menu-types")
    public ResponseEntity<?> getAllMenuTypes(
            @Valid @ParameterObject PaginatedRequest request) {
        return ResponseEntity.ok(menuTypeService.getAll(request));
    }

    @GetMapping("/menu-types/{menu-type-id}")
    public ResponseEntity<?> getAllMenuCategories(
            @PathVariable("menu-type-id") Long menuTypeId,
            @Valid @ParameterObject PaginatedRequest request) {
        return ResponseEntity.ok(menuCategoryService.getAll(menuTypeId, request));
    }

    @GetMapping("/menu-categories/{menu-category-id}/dishes")
    public ResponseEntity<?> getAllDishes(
            @PathVariable("menu-category-id") Long menuCategoryId,
            @Valid @ParameterObject PaginatedRequest request) {
        return ResponseEntity.ok(menuCategoryDishService.getAll(menuCategoryId, request));
    }
}
