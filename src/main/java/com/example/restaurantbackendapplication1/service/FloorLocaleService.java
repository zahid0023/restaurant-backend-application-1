package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.floor.floorlocale.CreateFloorLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.floor.floorlocale.UpdateFloorLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.FloorEntity;
import com.example.restaurantbackendapplication1.model.entity.FloorLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;

public interface FloorLocaleService {
    SuccessResponse create(FloorEntity floorEntity, LocaleEntity localeEntity,
                           CreateFloorLocaleRequest request);

    FloorLocaleEntity getEntityById(Long floorId, Long id);

    SuccessResponse update(FloorLocaleEntity entity, UpdateFloorLocaleRequest request);

    SuccessResponse delete(FloorLocaleEntity entity);
}
