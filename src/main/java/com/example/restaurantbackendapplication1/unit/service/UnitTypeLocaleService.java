package com.example.restaurantbackendapplication1.unit.service;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.unit.dto.request.unittypelocale.CreateUnitTypeLocaleRequest;
import com.example.restaurantbackendapplication1.unit.dto.request.unittypelocale.UpdateUnitTypeLocaleRequest;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.unit.model.entity.UnitTypeEntity;
import com.example.restaurantbackendapplication1.unit.model.entity.UnitTypeLocaleEntity;

public interface UnitTypeLocaleService {
    SuccessResponse create(UnitTypeEntity unitType, LocaleEntity locale, CreateUnitTypeLocaleRequest request);

    UnitTypeLocaleEntity getEntityById(Long unitTypeId, Long id);

    SuccessResponse update(UnitTypeLocaleEntity entity, UpdateUnitTypeLocaleRequest request);

    SuccessResponse delete(UnitTypeLocaleEntity entity);
}
