package com.example.restaurantbackendapplication1.dish.service;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dish.dto.request.dishvariantlocale.CreateDishVariantLocaleRequest;
import com.example.restaurantbackendapplication1.dish.dto.request.dishvariantlocale.UpdateDishVariantLocaleRequest;
import com.example.restaurantbackendapplication1.dish.model.entity.DishVariantEntity;
import com.example.restaurantbackendapplication1.dish.model.entity.DishVariantLocaleEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;

public interface DishVariantLocaleService {

    SuccessResponse create(DishVariantEntity dishVariantEntity,
                           LocaleEntity localeEntity,
                           CreateDishVariantLocaleRequest request);

    DishVariantLocaleEntity getEntityById(Long dishVariantId, Long id);

    SuccessResponse update(DishVariantLocaleEntity entity, UpdateDishVariantLocaleRequest request);

    SuccessResponse delete(DishVariantLocaleEntity entity);
}
