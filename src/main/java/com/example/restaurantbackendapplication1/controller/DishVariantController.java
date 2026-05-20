package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.dto.request.dishrecipeingredient.DishRecipeIngredientRequest;
import com.example.restaurantbackendapplication1.dto.request.dishvariant.CreateDishVariantRequest;
import com.example.restaurantbackendapplication1.dto.request.dishvariant.UpdateDishVariantRequest;
import com.example.restaurantbackendapplication1.dto.request.dishvariantlocale.CreateDishVariantLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.DishEntity;
import com.example.restaurantbackendapplication1.model.entity.DishVariantEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitEntity;
import com.example.restaurantbackendapplication1.service.DishService;
import com.example.restaurantbackendapplication1.service.DishVariantService;
import com.example.restaurantbackendapplication1.service.ItemService;
import com.example.restaurantbackendapplication1.service.LocaleService;
import com.example.restaurantbackendapplication1.service.MenuCategoryService;
import com.example.restaurantbackendapplication1.service.UnitService;
import com.example.restaurantbackendapplication1.utils.ItemUtils;
import com.example.restaurantbackendapplication1.utils.LocaleUtils;
import com.example.restaurantbackendapplication1.utils.UnitUtils;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/menu-categories/{menu-category-id}/dishes/{dish-id}/variants")
public class DishVariantController {

    private final DishVariantService dishVariantService;
    private final DishService dishService;
    private final MenuCategoryService menuCategoryService;
    private final LocaleService localeService;
    private final ItemService itemService;
    private final UnitService unitService;

    public DishVariantController(DishVariantService dishVariantService,
                                 DishService dishService,
                                 MenuCategoryService menuCategoryService,
                                 LocaleService localeService,
                                 ItemService itemService,
                                 UnitService unitService) {
        this.dishVariantService = dishVariantService;
        this.dishService = dishService;
        this.menuCategoryService = menuCategoryService;
        this.localeService = localeService;
        this.itemService = itemService;
        this.unitService = unitService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable("dish-id") Long dishId,
            @Valid @RequestBody CreateDishVariantRequest request) {
        menuCategoryService.getEntityById(menuCategoryId);
        DishEntity dishEntity = dishService.getEntityById(menuCategoryId, dishId);
        Map<Long, LocaleEntity> localeEntityMap = LocaleUtils.resolveLocaleMap(
                request.getLocales(), CreateDishVariantLocaleRequest::getLocaleId, localeService);
        Map<Long, ItemEntity> itemEntityMap = ItemUtils.resolveItemMap(
                request.getRecipe().getIngredients(), DishRecipeIngredientRequest::getItemId, itemService);
        Map<Long, UnitEntity> unitEntityMap = UnitUtils.resolveUnitMap(
                request.getRecipe().getIngredients(), DishRecipeIngredientRequest::getUnitId, unitService);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dishVariantService.create(request, dishEntity, localeEntityMap, itemEntityMap, unitEntityMap));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable("dish-id") Long dishId,
            @PathVariable Long id) {
        menuCategoryService.getEntityById(menuCategoryId);
        return ResponseEntity.ok(dishVariantService.getById(dishId, id));
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable("dish-id") Long dishId,
            @Valid @ParameterObject PaginatedRequest request) {
        menuCategoryService.getEntityById(menuCategoryId);
        return ResponseEntity.ok(dishVariantService.getAll(dishId, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable("dish-id") Long dishId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateDishVariantRequest request) {
        menuCategoryService.getEntityById(menuCategoryId);
        DishVariantEntity entity = dishVariantService.getEntityById(dishId, id);
        return ResponseEntity.ok(dishVariantService.update(entity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable("dish-id") Long dishId,
            @PathVariable Long id) {
        menuCategoryService.getEntityById(menuCategoryId);
        return ResponseEntity.ok(dishVariantService.delete(dishId, id));
    }
}
