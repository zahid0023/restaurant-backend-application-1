package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.dishvariantlocale.CreateDishVariantLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.dishvariantlocale.UpdateDishVariantLocaleRequest;
import com.example.restaurantbackendapplication1.dto.response.DishVariantLocaleResponse;
import com.example.restaurantbackendapplication1.model.entity.DishVariantEntity;
import com.example.restaurantbackendapplication1.model.entity.DishVariantLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.projection.DishVariantLocaleSummary;

public interface DishVariantLocaleService {

    SuccessResponse create(DishVariantEntity dishVariantEntity,
                           LocaleEntity localeEntity,
                           CreateDishVariantLocaleRequest request);

    DishVariantLocaleEntity getEntityById(Long dishVariantId, Long id);

    DishVariantLocaleResponse getById(Long dishVariantId, Long id);

    PaginatedResponse<DishVariantLocaleSummary> getAll(Long dishVariantId, PaginatedRequest request);

    SuccessResponse update(DishVariantLocaleEntity entity,
                           LocaleEntity localeEntity,
                           UpdateDishVariantLocaleRequest request);

    SuccessResponse delete(DishVariantLocaleEntity entity);
}
