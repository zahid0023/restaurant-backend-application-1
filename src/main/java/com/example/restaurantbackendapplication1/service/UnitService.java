package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.unit.CreateUnitRequest;
import com.example.restaurantbackendapplication1.dto.request.unit.UpdateUnitRequest;
import com.example.restaurantbackendapplication1.dto.response.UnitResponse;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitTypeEntity;
import com.example.restaurantbackendapplication1.model.projection.UnitSummary;

import java.util.Map;

public interface UnitService {
    SuccessResponse create(CreateUnitRequest request,
                           UnitTypeEntity unitTypeEntity,
                           Map<Long, LocaleEntity> localeEntityMap);

    UnitEntity getEntityById(Long unitId);

    UnitEntity getEntityById(Long unitTypeId, Long id);

    UnitResponse getById(Long unitTypeId, Long id);

    PaginatedResponse<UnitSummary> getAll(Long unitTypeId, PaginatedRequest request);

    SuccessResponse update(UnitEntity entity,
                           UpdateUnitRequest request);

    SuccessResponse delete(Long unitTypeId, Long id);
}
