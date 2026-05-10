package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.diningspace.CreateDiningSpaceRequest;
import com.example.restaurantbackendapplication1.dto.request.diningspace.UpdateDiningSpaceRequest;
import com.example.restaurantbackendapplication1.dto.response.DiningSpaceResponse;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceEntity;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceTypeEntity;
import com.example.restaurantbackendapplication1.model.entity.FloorEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.projection.DiningSpaceSummary;

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
                           UpdateDiningSpaceRequest request,
                           DiningSpaceTypeEntity diningSpaceTypeEntity,
                           FloorEntity floorEntity);

    SuccessResponse delete(Long id);
}
