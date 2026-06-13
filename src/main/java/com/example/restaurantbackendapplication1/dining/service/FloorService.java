package com.example.restaurantbackendapplication1.dining.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dining.dto.request.floor.CreateFloorRequest;
import com.example.restaurantbackendapplication1.dining.dto.request.floor.UpdateFloorRequest;
import com.example.restaurantbackendapplication1.dining.dto.response.FloorResponse;
import com.example.restaurantbackendapplication1.dining.model.entity.FloorEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.dining.model.projection.FloorSummary;

import java.util.Map;

public interface FloorService {
    SuccessResponse create(CreateFloorRequest request, Map<Long, LocaleEntity> localeEntityMap);

    FloorEntity getEntityById(Long id);

    FloorResponse getById(Long id);

    PaginatedResponse<FloorSummary> getAll(PaginatedRequest request);

    SuccessResponse update(FloorEntity entity, UpdateFloorRequest request);

    SuccessResponse delete(Long id);
}
