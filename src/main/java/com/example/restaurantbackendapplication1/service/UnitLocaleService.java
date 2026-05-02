package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.unitlocale.CreateUnitLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.unitlocale.UpdateUnitLocaleRequest;
import com.example.restaurantbackendapplication1.dto.response.UnitLocaleResponse;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitLocaleEntity;
import com.example.restaurantbackendapplication1.model.projection.UnitLocaleSummary;

public interface UnitLocaleService {
    SuccessResponse create(UnitEntity unitEntity, LocaleEntity locale, CreateUnitLocaleRequest request);

    UnitLocaleEntity getEntityById(Long id, UnitEntity unitEntity);

    UnitLocaleResponse getById(Long id, UnitEntity unitEntity);

    PaginatedResponse<UnitLocaleSummary> getAll(UnitEntity unitEntity, PaginatedRequest request);

    SuccessResponse update(UnitLocaleEntity entity, LocaleEntity locale, UpdateUnitLocaleRequest request);

    SuccessResponse delete(UnitLocaleEntity entity);
}
