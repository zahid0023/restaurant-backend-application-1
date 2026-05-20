package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.dishvariantlocale.CreateDishVariantLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.dishvariantlocale.UpdateDishVariantLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.DishVariantEntity;
import com.example.restaurantbackendapplication1.model.entity.DishVariantLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;

public interface DishVariantLocaleService {

    SuccessResponse create(DishVariantEntity dishVariantEntity,
                           LocaleEntity localeEntity,
                           CreateDishVariantLocaleRequest request);

    DishVariantLocaleEntity getEntityById(Long dishVariantId, Long id);

    SuccessResponse update(DishVariantLocaleEntity entity, UpdateDishVariantLocaleRequest request);

    SuccessResponse delete(DishVariantLocaleEntity entity);
}
