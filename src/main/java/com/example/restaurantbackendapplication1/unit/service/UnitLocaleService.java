package com.example.restaurantbackendapplication1.unit.service;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.unit.dto.request.unitlocale.CreateUnitLocaleRequest;
import com.example.restaurantbackendapplication1.unit.dto.request.unitlocale.UpdateUnitLocaleRequest;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.unit.model.entity.UnitEntity;
import com.example.restaurantbackendapplication1.unit.model.entity.UnitLocaleEntity;

public interface UnitLocaleService {
    SuccessResponse create(UnitEntity unitEntity, LocaleEntity locale, CreateUnitLocaleRequest request);

    UnitLocaleEntity getEntityById(Long id, UnitEntity unitEntity);

    SuccessResponse update(UnitLocaleEntity entity, UpdateUnitLocaleRequest request);

    SuccessResponse delete(UnitLocaleEntity entity);
}
