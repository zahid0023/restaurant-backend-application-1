package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.floorlocale.CreateFloorLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.floorlocale.UpdateFloorLocaleRequest;
import com.example.restaurantbackendapplication1.dto.response.FloorLocaleResponse;
import com.example.restaurantbackendapplication1.model.entity.FloorEntity;
import com.example.restaurantbackendapplication1.model.entity.FloorLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.projection.FloorLocaleSummary;

public interface FloorLocaleService {
    SuccessResponse create(FloorEntity floorEntity, LocaleEntity localeEntity,
                           CreateFloorLocaleRequest request);

    FloorLocaleEntity getEntityById(Long id, FloorEntity floorEntity);

    FloorLocaleResponse getById(Long id, FloorEntity floorEntity);

    PaginatedResponse<FloorLocaleSummary> getAll(FloorEntity floorEntity, PaginatedRequest request);

    SuccessResponse update(FloorLocaleEntity entity, LocaleEntity localeEntity,
                           UpdateFloorLocaleRequest request);

    SuccessResponse delete(FloorLocaleEntity entity);
}
