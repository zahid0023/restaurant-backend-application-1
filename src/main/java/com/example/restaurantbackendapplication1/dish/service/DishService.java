package com.example.restaurantbackendapplication1.dish.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dish.dto.request.CreateDishRequest;
import com.example.restaurantbackendapplication1.dish.dto.request.UpdateDishRequest;
import com.example.restaurantbackendapplication1.dish.dto.response.DishResponse;
import com.example.restaurantbackendapplication1.dish.model.entity.DishEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.dish.model.projection.DishSummary;

import java.util.Map;

public interface DishService {

    SuccessResponse create(CreateDishRequest request, Map<Long, LocaleEntity> localeEntityMap);

    DishEntity getEntityById(Long id);

    DishResponse getById(Long id);

    PaginatedResponse<DishSummary> getAll(PaginatedRequest request);

    SuccessResponse update(DishEntity entity, UpdateDishRequest request);

    SuccessResponse delete(Long id);
}
