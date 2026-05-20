package com.example.restaurantbackendapplication1.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.dto.request.dish.CreateDishRequest;
import com.example.restaurantbackendapplication1.dto.request.dish.UpdateDishRequest;
import com.example.restaurantbackendapplication1.dto.request.dishlocale.CreateDishLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.dishrecipeingredient.DishRecipeIngredientRequest;
import com.example.restaurantbackendapplication1.dto.request.dishvariant.CreateDishVariantRequest;
import com.example.restaurantbackendapplication1.model.entity.DishEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuCategoryEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitEntity;
import com.example.restaurantbackendapplication1.service.DishService;
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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/menu-categories/{menu-category-id}/dishes")
public class DishController {

    private final DishService dishService;
    private final MenuCategoryService menuCategoryService;
    private final LocaleService localeService;
    private final ItemService itemService;
    private final UnitService unitService;

    public DishController(DishService dishService,
                          MenuCategoryService menuCategoryService,
                          LocaleService localeService,
                          ItemService itemService,
                          UnitService unitService) {
        this.dishService = dishService;
        this.menuCategoryService = menuCategoryService;
        this.localeService = localeService;
        this.itemService = itemService;
        this.unitService = unitService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable("menu-category-id") Long menuCategoryId,
            @Valid @RequestBody CreateDishRequest request) {
        MenuCategoryEntity menuCategoryEntity = menuCategoryService.getEntityById(menuCategoryId);

        Map<Long, LocaleEntity> localeEntityMap = LocaleUtils.resolveLocaleMap(
                request.getLocales(), CreateDishLocaleRequest::getLocaleId, localeService);

        List<DishRecipeIngredientRequest> allIngredients = flattenIngredients(request.getVariants());
        Map<Long, ItemEntity> itemEntityMap = ItemUtils.resolveItemMap(
                allIngredients, DishRecipeIngredientRequest::getItemId, itemService);
        Map<Long, UnitEntity> unitEntityMap = UnitUtils.resolveUnitMap(
                allIngredients, DishRecipeIngredientRequest::getUnitId, unitService);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dishService.create(request, menuCategoryEntity, localeEntityMap, itemEntityMap, unitEntityMap));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable Long id) {
        return ResponseEntity.ok(dishService.getById(menuCategoryId, id));
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PathVariable("menu-category-id") Long menuCategoryId,
            @Valid @ParameterObject PaginatedRequest request) {
        return ResponseEntity.ok(dishService.getAll(menuCategoryId, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateDishRequest request) {
        DishEntity entity = dishService.getEntityById(menuCategoryId, id);
        return ResponseEntity.ok(dishService.update(entity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("menu-category-id") Long menuCategoryId,
            @PathVariable Long id) {
        return ResponseEntity.ok(dishService.delete(menuCategoryId, id));
    }

    private List<DishRecipeIngredientRequest> flattenIngredients(List<CreateDishVariantRequest> variants) {
        if (variants == null || variants.isEmpty()) return Collections.emptyList();
        return variants.stream()
                .flatMap(v -> v.getRecipe().getIngredients().stream())
                .collect(Collectors.toList());
    }
}
