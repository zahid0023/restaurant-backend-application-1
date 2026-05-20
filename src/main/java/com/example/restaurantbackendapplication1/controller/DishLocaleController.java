package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.dto.request.dishlocale.CreateDishLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.dishlocale.UpdateDishLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.DishEntity;
import com.example.restaurantbackendapplication1.model.entity.DishesLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.service.DishLocaleService;
import com.example.restaurantbackendapplication1.service.DishService;
import com.example.restaurantbackendapplication1.service.LocaleService;
import com.example.restaurantbackendapplication1.service.MenuCategoryService;
import com.example.restaurantbackendapplication1.service.MenuService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/locales")
public class DishLocaleController {

    private final DishLocaleService dishLocaleService;
    private final DishService dishService;
    private final MenuCategoryService menuCategoryService;
    private final MenuService menuService;
    private final LocaleService localeService;

    public DishLocaleController(DishLocaleService dishLocaleService,
                                DishService dishService,
                                MenuCategoryService menuCategoryService,
                                MenuService menuService,
                                LocaleService localeService) {
        this.dishLocaleService = dishLocaleService;
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
            @Valid @RequestBody CreateDishLocaleRequest request) {
        menuService.getEntityById(menuId);
        menuCategoryService.getEntityById(menuCategoryId);
        DishEntity dishEntity = dishService.getEntityById(menuCategoryId, dishId);
        LocaleEntity localeEntity = localeService.getEntityById(request.getLocaleId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dishLocaleService.create(dishEntity, localeEntity, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable("dish-id") Long dishId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateDishLocaleRequest request) {
        menuService.getEntityById(menuId);
        menuCategoryService.getEntityById(menuCategoryId);
        DishesLocaleEntity entity = dishLocaleService.getEntityById(dishId, id);
        return ResponseEntity.ok(dishLocaleService.update(entity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable("dish-id") Long dishId,
            @PathVariable Long id) {
        menuService.getEntityById(menuId);
        menuCategoryService.getEntityById(menuCategoryId);
        DishesLocaleEntity entity = dishLocaleService.getEntityById(dishId, id);
        return ResponseEntity.ok(dishLocaleService.delete(entity));
    }
}
