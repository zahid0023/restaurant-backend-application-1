package com.example.restaurantbackendapplication1.dining.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dining.dto.request.diningspace.CreateDiningSpaceRequest;
import com.example.restaurantbackendapplication1.dining.dto.request.diningspace.UpdateDiningSpaceRequest;
import com.example.restaurantbackendapplication1.dining.dto.response.DiningSpaceResponse;
import com.example.restaurantbackendapplication1.dining.model.entity.DiningSpaceEntity;
import com.example.restaurantbackendapplication1.dining.model.entity.DiningSpaceTypeEntity;
import com.example.restaurantbackendapplication1.dining.model.entity.FloorEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.dining.model.projection.DiningSpaceSummary;

import java.util.Map;

public interface DiningSpaceService {
    SuccessResponse create(CreateDiningSpaceRequest request,
                           DiningSpaceTypeEntity diningSpaceTypeEntity,
                           FloorEntity floorEntity,
                           Map<Long, LocaleEntity> localeEntityMap);

    DiningSpaceEntity getEntityById(Long id);

    DiningSpaceResponse getById(Long id);

    PaginatedResponse<DiningSpaceSummary> getAll(PaginatedRequest request);

    SuccessResponse update(DiningSpaceEntity entity,
                           UpdateDiningSpaceRequest request);

    SuccessResponse delete(Long id);
}
