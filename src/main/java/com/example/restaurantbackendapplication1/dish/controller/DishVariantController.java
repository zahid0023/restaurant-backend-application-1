package com.example.restaurantbackendapplication1.dish.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.dish.dto.request.dishvariant.CreateDishVariantRequest;
import com.example.restaurantbackendapplication1.dish.dto.request.dishvariant.UpdateDishVariantRequest;
import com.example.restaurantbackendapplication1.dish.dto.request.dishvariantingredient.CreateDishVariantIngredientRequest;
import com.example.restaurantbackendapplication1.dish.dto.request.dishvariantlocale.CreateDishVariantLocaleRequest;
import com.example.restaurantbackendapplication1.dish.model.entity.DishEntity;
import com.example.restaurantbackendapplication1.dish.model.entity.DishVariantEntity;
import com.example.restaurantbackendapplication1.item.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.unit.model.entity.UnitEntity;
import com.example.restaurantbackendapplication1.dish.service.DishService;
import com.example.restaurantbackendapplication1.dish.service.DishVariantService;
import com.example.restaurantbackendapplication1.item.service.ItemService;
import com.example.restaurantbackendapplication1.locale.service.LocaleService;
import com.example.restaurantbackendapplication1.unit.service.UnitService;
import com.example.restaurantbackendapplication1.commons.utils.LocaleUtils;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/dishes/{dish-id}/variants")
public class DishVariantController {

    private final DishVariantService dishVariantService;
    private final DishService dishService;
    private final LocaleService localeService;
    private final ItemService itemService;
    private final UnitService unitService;

    public DishVariantController(DishVariantService dishVariantService,
                                  DishService dishService,
                                  LocaleService localeService,
                                  ItemService itemService,
                                  UnitService unitService) {
        this.dishVariantService = dishVariantService;
        this.dishService = dishService;
        this.localeService = localeService;
        this.itemService = itemService;
        this.unitService = unitService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable("dish-id") Long dishId,
            @Valid @RequestBody CreateDishVariantRequest request) {
        DishEntity dishEntity = dishService.getEntityById(dishId);
        Map<Long, LocaleEntity> localeEntityMap = LocaleUtils.resolveLocaleMap(
                request.getLocales(), CreateDishVariantLocaleRequest::getLocaleId, localeService);
        Map<Long, ItemEntity> itemEntityMap = LocaleUtils.resolveEntityMap(
                request.getIngredients(), CreateDishVariantIngredientRequest::getItemId,
                itemService::getAll, ItemEntity::getId);
        Map<Long, UnitEntity> unitEntityMap = LocaleUtils.resolveEntityMap(
                request.getIngredients(), CreateDishVariantIngredientRequest::getUnitId,
                unitService::getAll, UnitEntity::getId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dishVariantService.create(dishEntity, request, localeEntityMap, itemEntityMap, unitEntityMap));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable("dish-id") Long dishId,
            @PathVariable Long id) {
        return ResponseEntity.ok(dishVariantService.getById(dishId, id));
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PathVariable("dish-id") Long dishId,
            @Valid @ParameterObject PaginatedRequest request) {
        return ResponseEntity.ok(dishVariantService.getAll(dishId, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("dish-id") Long dishId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateDishVariantRequest request) {
        DishVariantEntity entity = dishVariantService.getEntityById(dishId, id);
        return ResponseEntity.ok(dishVariantService.update(entity, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("dish-id") Long dishId,
            @PathVariable Long id) {
        DishVariantEntity entity = dishVariantService.getEntityById(dishId, id);
        return ResponseEntity.ok(dishVariantService.delete(entity));
    }
}
