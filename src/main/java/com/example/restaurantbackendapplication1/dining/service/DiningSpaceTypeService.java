package com.example.restaurantbackendapplication1.dining.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dining.dto.request.diningspacetype.CreateDiningSpaceTypeRequest;
import com.example.restaurantbackendapplication1.dining.dto.request.diningspacetype.UpdateDiningSpaceTypeRequest;
import com.example.restaurantbackendapplication1.dining.dto.response.DiningSpaceTypeResponse;
import com.example.restaurantbackendapplication1.dining.model.entity.DiningSpaceTypeEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.dining.model.projection.DiningSpaceTypeSummary;

import java.util.Map;

public interface DiningSpaceTypeService {
    SuccessResponse create(CreateDiningSpaceTypeRequest request,
                           Map<Long, LocaleEntity> localeEntityMap);

    DiningSpaceTypeEntity getEntityById(Long id);

    DiningSpaceTypeResponse getById(Long id);

    PaginatedResponse<DiningSpaceTypeSummary> getAll(PaginatedRequest request);

    SuccessResponse update(DiningSpaceTypeEntity entity, UpdateDiningSpaceTypeRequest request);

    SuccessResponse delete(Long id);
}
