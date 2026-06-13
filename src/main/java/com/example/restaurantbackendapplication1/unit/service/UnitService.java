package com.example.restaurantbackendapplication1.unit.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.unit.dto.request.CreateUnitRequest;
import com.example.restaurantbackendapplication1.unit.dto.request.UpdateUnitRequest;
import com.example.restaurantbackendapplication1.unit.dto.response.UnitResponse;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.unit.model.entity.UnitEntity;
import com.example.restaurantbackendapplication1.unit.model.entity.UnitTypeEntity;
import com.example.restaurantbackendapplication1.unit.model.projection.UnitSummary;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UnitService {
    SuccessResponse create(CreateUnitRequest request,
                           UnitTypeEntity unitTypeEntity,
                           Map<Long, LocaleEntity> localeEntityMap);

    UnitEntity getEntityById(Long id);

    List<UnitEntity> getAll(Set<Long> ids);

    UnitEntity getEntityById(Long unitTypeId, Long id);

    UnitResponse getById(Long unitTypeId, Long id);

    PaginatedResponse<UnitSummary> getAll(Long unitTypeId, PaginatedRequest request);

    SuccessResponse update(UnitEntity entity, UpdateUnitRequest request);

    SuccessResponse delete(UnitEntity entity);
}
