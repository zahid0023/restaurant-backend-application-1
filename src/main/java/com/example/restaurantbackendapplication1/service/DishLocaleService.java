package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.dishlocale.CreateDishLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.dishlocale.UpdateDishLocaleRequest;
import com.example.restaurantbackendapplication1.dto.response.DishLocaleResponse;
import com.example.restaurantbackendapplication1.model.entity.DishEntity;
import com.example.restaurantbackendapplication1.model.entity.DishesLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.projection.DishLocaleSummary;

public interface DishLocaleService {

    SuccessResponse create(DishEntity dishEntity,
                           LocaleEntity localeEntity,
                           CreateDishLocaleRequest request);

    DishesLocaleEntity getEntityById(Long dishId, Long id);

    DishLocaleResponse getById(Long dishId, Long id);

    PaginatedResponse<DishLocaleSummary> getAll(Long dishId, PaginatedRequest request);

    SuccessResponse update(DishesLocaleEntity entity,
                           LocaleEntity localeEntity,
                           UpdateDishLocaleRequest request);

    SuccessResponse delete(DishesLocaleEntity entity);
}
