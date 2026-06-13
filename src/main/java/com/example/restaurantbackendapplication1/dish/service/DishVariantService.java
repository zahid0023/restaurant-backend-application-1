package com.example.restaurantbackendapplication1.dish.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dish.dto.request.dishvariant.CreateDishVariantRequest;
import com.example.restaurantbackendapplication1.dish.dto.request.dishvariant.UpdateDishVariantRequest;
import com.example.restaurantbackendapplication1.dish.dto.response.DishVariantResponse;
import com.example.restaurantbackendapplication1.dish.model.entity.DishEntity;
import com.example.restaurantbackendapplication1.dish.model.entity.DishVariantEntity;
import com.example.restaurantbackendapplication1.item.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.unit.model.entity.UnitEntity;
import com.example.restaurantbackendapplication1.dish.model.projection.DishVariantSummary;

import java.util.Map;

public interface DishVariantService {

    SuccessResponse create(DishEntity dishEntity,
                           CreateDishVariantRequest request,
                           Map<Long, LocaleEntity> localeEntityMap,
                           Map<Long, ItemEntity> itemEntityMap,
                           Map<Long, UnitEntity> unitEntityMap);

    DishVariantEntity getEntityById(Long dishId, Long id);

    DishVariantResponse getById(Long dishId, Long id);

    PaginatedResponse<DishVariantSummary> getAll(Long dishId, PaginatedRequest request);

    SuccessResponse update(DishVariantEntity entity, UpdateDishVariantRequest request);

    SuccessResponse delete(DishVariantEntity entity);
}
