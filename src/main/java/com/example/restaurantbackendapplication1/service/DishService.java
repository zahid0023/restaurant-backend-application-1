package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.dish.CreateDishRequest;
import com.example.restaurantbackendapplication1.dto.request.dish.UpdateDishRequest;
import com.example.restaurantbackendapplication1.dto.response.DishResponse;
import com.example.restaurantbackendapplication1.model.entity.DishEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.projection.DishSummary;

import java.util.Map;

public interface DishService {

    SuccessResponse create(CreateDishRequest request, Map<Long, LocaleEntity> localeEntityMap);

    DishEntity getEntityById(Long id);

    DishResponse getById(Long id);

    PaginatedResponse<DishSummary> getAll(PaginatedRequest request);

    SuccessResponse update(DishEntity entity, UpdateDishRequest request);

    SuccessResponse delete(Long id);
}
