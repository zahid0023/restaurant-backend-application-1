package com.example.restaurantbackendapplication1.dining.service;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dining.dto.request.floorlocale.CreateFloorLocaleRequest;
import com.example.restaurantbackendapplication1.dining.dto.request.floorlocale.UpdateFloorLocaleRequest;
import com.example.restaurantbackendapplication1.dining.model.entity.FloorEntity;
import com.example.restaurantbackendapplication1.dining.model.entity.FloorLocaleEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;

public interface FloorLocaleService {
    SuccessResponse create(FloorEntity floorEntity, LocaleEntity localeEntity,
                           CreateFloorLocaleRequest request);

    FloorLocaleEntity getEntityById(Long floorId, Long id);

    SuccessResponse update(FloorLocaleEntity entity, UpdateFloorLocaleRequest request);

    SuccessResponse delete(FloorLocaleEntity entity);
}
