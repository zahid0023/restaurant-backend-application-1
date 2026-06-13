package com.example.restaurantbackendapplication1.unit.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.unit.dto.request.unittype.CreateUnitTypeRequest;
import com.example.restaurantbackendapplication1.unit.dto.request.unittype.UpdateUnitTypeRequest;
import com.example.restaurantbackendapplication1.unit.dto.response.UnitTypeResponse;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.unit.model.entity.UnitTypeEntity;
import com.example.restaurantbackendapplication1.unit.model.projection.UnitTypeSummary;

import java.util.Map;

public interface UnitTypeService {
    SuccessResponse create(CreateUnitTypeRequest request, Map<Long, LocaleEntity> localeEntityMap);

    UnitTypeEntity getEntityById(Long id);

    UnitTypeResponse getById(Long id);

    PaginatedResponse<UnitTypeSummary> getAll(PaginatedRequest request);

    SuccessResponse update(UnitTypeEntity entity, UpdateUnitTypeRequest request);

    SuccessResponse delete(Long id);
}
