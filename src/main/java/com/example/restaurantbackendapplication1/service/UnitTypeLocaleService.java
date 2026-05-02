package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.unittypelocale.CreateUnitTypeLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.unittypelocale.UpdateUnitTypeLocaleRequest;
import com.example.restaurantbackendapplication1.dto.response.UnitTypeLocaleResponse;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitTypeEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitTypeLocaleEntity;
import com.example.restaurantbackendapplication1.model.projection.UnitTypeLocaleSummary;

public interface UnitTypeLocaleService {
    SuccessResponse create(UnitTypeEntity unitType, LocaleEntity locale, CreateUnitTypeLocaleRequest request);

    UnitTypeLocaleEntity getEntityById(Long id, UnitTypeEntity unitType);

    UnitTypeLocaleResponse getById(Long id, UnitTypeEntity unitType);

    PaginatedResponse<UnitTypeLocaleSummary> getAll(UnitTypeEntity unitType, PaginatedRequest request);

    SuccessResponse update(UnitTypeLocaleEntity entity, LocaleEntity locale, UpdateUnitTypeLocaleRequest request);

    SuccessResponse delete(UnitTypeLocaleEntity entity);
}
