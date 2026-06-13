package com.example.restaurantbackendapplication1.dish.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dish.dto.request.dishvariantingredient.CreateDishVariantIngredientRequest;
import com.example.restaurantbackendapplication1.dish.dto.request.dishvariantingredient.UpdateDishVariantIngredientRequest;
import com.example.restaurantbackendapplication1.dish.dto.response.DishVariantIngredientResponse;
import com.example.restaurantbackendapplication1.dish.model.entity.DishVariantEntity;
import com.example.restaurantbackendapplication1.dish.model.entity.DishVariantIngredientEntity;
import com.example.restaurantbackendapplication1.item.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.unit.model.entity.UnitEntity;
import com.example.restaurantbackendapplication1.dish.model.projection.DishVariantIngredientSummary;

public interface DishVariantIngredientService {

    SuccessResponse create(DishVariantEntity dishVariantEntity,
                           ItemEntity itemEntity,
                           UnitEntity unitEntity,
                           CreateDishVariantIngredientRequest request);

    DishVariantIngredientEntity getEntityById(Long dishVariantId, Long id);

    DishVariantIngredientResponse getById(Long dishVariantId, Long id);

    PaginatedResponse<DishVariantIngredientSummary> getAll(Long dishVariantId, PaginatedRequest request);

    SuccessResponse update(DishVariantIngredientEntity entity,
                           UpdateDishVariantIngredientRequest request,
                           UnitEntity unitEntity);

    SuccessResponse delete(DishVariantIngredientEntity entity);
}
