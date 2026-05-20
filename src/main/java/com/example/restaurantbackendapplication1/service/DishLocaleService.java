package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.dishlocale.CreateDishLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.dishlocale.UpdateDishLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.DishEntity;
import com.example.restaurantbackendapplication1.model.entity.DishesLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;

public interface DishLocaleService {

    SuccessResponse create(DishEntity dishEntity,
                           LocaleEntity localeEntity,
                           CreateDishLocaleRequest request);

    DishesLocaleEntity getEntityById(Long dishId, Long id);

    SuccessResponse update(DishesLocaleEntity entity, UpdateDishLocaleRequest request);

    SuccessResponse delete(DishesLocaleEntity entity);
}
