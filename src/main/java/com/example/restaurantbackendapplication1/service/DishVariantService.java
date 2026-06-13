package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.dishvariant.CreateDishVariantRequest;
import com.example.restaurantbackendapplication1.dto.request.dishvariant.UpdateDishVariantRequest;
import com.example.restaurantbackendapplication1.dto.response.DishVariantResponse;
import com.example.restaurantbackendapplication1.model.entity.DishEntity;
import com.example.restaurantbackendapplication1.model.entity.DishVariantEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitEntity;
import com.example.restaurantbackendapplication1.model.projection.DishVariantSummary;

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
