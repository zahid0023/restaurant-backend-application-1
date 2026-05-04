package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.dto.request.dishvariant.CreateDishVariantRequest;
import com.example.restaurantbackendapplication1.dto.request.dishvariant.UpdateDishVariantRequest;
import com.example.restaurantbackendapplication1.dto.request.dishvariantlocale.DishVariantLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.DishEntity;
import com.example.restaurantbackendapplication1.model.entity.DishVariantEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.service.DishService;
import com.example.restaurantbackendapplication1.service.DishVariantService;
import com.example.restaurantbackendapplication1.service.LocaleService;
import com.example.restaurantbackendapplication1.service.MenuCategoryService;
import com.example.restaurantbackendapplication1.service.MenuService;
import com.example.restaurantbackendapplication1.utils.LocaleUtils;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/menus/{menu-id}/menu-categories/{menu-category-id}/dishes/{dish-id}/variants")
public class DishVariantController {

    private final DishVariantService dishVariantService;
    private final DishService dishService;
    private final MenuCategoryService menuCategoryService;
    private final MenuService menuService;
    private final LocaleService localeService;

    public DishVariantController(DishVariantService dishVariantService,
                                 DishService dishService,
                                 MenuCategoryService menuCategoryService,
                                 MenuService menuService,
                                 LocaleService localeService) {
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
            @Valid @RequestBody CreateDishVariantRequest request) {
        menuService.getEntityById(menuId);
        menuCategoryService.getEntityById(menuId, menuCategoryId);
        DishEntity dishEntity = dishService.getEntityById(menuCategoryId, dishId);
        Map<Long, LocaleEntity> localeEntityMap = LocaleUtils.resolveLocaleMap(
                request.getLocales(), DishVariantLocaleRequest::getLocaleId, localeService);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dishVariantService.create(request, dishEntity, localeEntityMap));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable("dish-id") Long dishId,
            @PathVariable Long id) {
        menuService.getEntityById(menuId);
        menuCategoryService.getEntityById(menuId, menuCategoryId);
        return ResponseEntity.ok(dishVariantService.getById(dishId, id));
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable("dish-id") Long dishId,
            @Valid @ParameterObject PaginatedRequest request) {
        menuService.getEntityById(menuId);
        menuCategoryService.getEntityById(menuId, menuCategoryId);
        return ResponseEntity.ok(dishVariantService.getAll(dishId, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable("dish-id") Long dishId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateDishVariantRequest request) {
        menuService.getEntityById(menuId);
        menuCategoryService.getEntityById(menuId, menuCategoryId);
        DishVariantEntity entity = dishVariantService.getEntityById(dishId, id);
        return ResponseEntity.ok(dishVariantService.update(entity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("menu-id") Long menuId,
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable("dish-id") Long dishId,
            @PathVariable Long id) {
        menuService.getEntityById(menuId);
        menuCategoryService.getEntityById(menuId, menuCategoryId);
        return ResponseEntity.ok(dishVariantService.delete(dishId, id));
    }
}
