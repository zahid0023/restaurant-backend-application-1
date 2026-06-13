package com.example.restaurantbackendapplication1.dish.controller;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.dish.dto.request.dishvariantingredient.CreateDishVariantIngredientRequest;
import com.example.restaurantbackendapplication1.dish.dto.request.dishvariantingredient.UpdateDishVariantIngredientRequest;
import com.example.restaurantbackendapplication1.dish.model.entity.DishVariantEntity;
import com.example.restaurantbackendapplication1.dish.model.entity.DishVariantIngredientEntity;
import com.example.restaurantbackendapplication1.item.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.unit.model.entity.UnitEntity;
import com.example.restaurantbackendapplication1.dish.service.DishVariantIngredientService;
import com.example.restaurantbackendapplication1.dish.service.DishVariantService;
import com.example.restaurantbackendapplication1.item.service.ItemService;
import com.example.restaurantbackendapplication1.unit.service.UnitService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dishes/{dish-id}/variants/{variant-id}/ingredients")
public class DishVariantIngredientController {

    private final DishVariantIngredientService dishVariantIngredientService;
    private final DishVariantService dishVariantService;
    private final ItemService itemService;
    private final UnitService unitService;

    public DishVariantIngredientController(DishVariantIngredientService dishVariantIngredientService,
                                            DishVariantService dishVariantService,
                                            ItemService itemService,
                                            UnitService unitService) {
        this.dishVariantIngredientService = dishVariantIngredientService;
        this.dishVariantService = dishVariantService;
        this.itemService = itemService;
        this.unitService = unitService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @PathVariable("dish-id") Long dishId,
            @PathVariable("variant-id") Long variantId,
            @Valid @RequestBody CreateDishVariantIngredientRequest request) {
        DishVariantEntity dishVariantEntity = dishVariantService.getEntityById(dishId, variantId);
        ItemEntity itemEntity = itemService.getEntityById(request.getItemId());
        UnitEntity unitEntity = unitService.getEntityById(request.getUnitId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dishVariantIngredientService.create(dishVariantEntity, itemEntity, unitEntity, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable("dish-id") Long dishId,
            @PathVariable("variant-id") Long variantId,
            @PathVariable Long id) {
        return ResponseEntity.ok(dishVariantIngredientService.getById(variantId, id));
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PathVariable("dish-id") Long dishId,
            @PathVariable("variant-id") Long variantId,
            @Valid @ParameterObject PaginatedRequest request) {
        return ResponseEntity.ok(dishVariantIngredientService.getAll(variantId, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable("dish-id") Long dishId,
            @PathVariable("variant-id") Long variantId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateDishVariantIngredientRequest request) {
        DishVariantIngredientEntity entity = dishVariantIngredientService.getEntityById(variantId, id);
        UnitEntity unitEntity = unitService.getEntityById(request.getUnitId());
        return ResponseEntity.ok(dishVariantIngredientService.update(entity, request, unitEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("dish-id") Long dishId,
            @PathVariable("variant-id") Long variantId,
            @PathVariable Long id) {
        DishVariantIngredientEntity entity = dishVariantIngredientService.getEntityById(variantId, id);
        return ResponseEntity.ok(dishVariantIngredientService.delete(entity));
    }
}
