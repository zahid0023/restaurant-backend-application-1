package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.unittype.CreateUnitTypeRequest;
import com.example.restaurantbackendapplication1.dto.request.unittype.UpdateUnitTypeRequest;
import com.example.restaurantbackendapplication1.dto.response.UnitTypeResponse;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitTypeEntity;
import com.example.restaurantbackendapplication1.model.projection.UnitTypeSummary;

import java.util.Map;

public interface UnitTypeService {
    SuccessResponse create(CreateUnitTypeRequest request, Map<Long, LocaleEntity> localeEntityMap);

    UnitTypeEntity getEntityById(Long id);

    UnitTypeResponse getById(Long id);

    PaginatedResponse<UnitTypeSummary> getAll(PaginatedRequest request);

    SuccessResponse update(UnitTypeEntity entity, UpdateUnitTypeRequest request);

    SuccessResponse delete(Long id);
}
