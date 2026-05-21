package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.floor.CreateFloorRequest;
import com.example.restaurantbackendapplication1.dto.request.floor.UpdateFloorRequest;
import com.example.restaurantbackendapplication1.dto.response.floors.FloorResponse;
import com.example.restaurantbackendapplication1.model.entity.FloorEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.projection.FloorSummary;

import java.util.Map;

public interface FloorService {
    SuccessResponse create(CreateFloorRequest request, Map<Long, LocaleEntity> localeEntityMap);

    FloorEntity getEntityById(Long id);

    FloorResponse getById(Long id);

    PaginatedResponse<FloorSummary> getAll(PaginatedRequest request);

    SuccessResponse update(FloorEntity entity, UpdateFloorRequest request);

    SuccessResponse delete(Long id);
}
