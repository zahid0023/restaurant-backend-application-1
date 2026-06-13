package com.example.restaurantbackendapplication1.dining.service;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dining.dto.request.diningspacetypelocale.CreateDiningSpaceTypeLocaleRequest;
import com.example.restaurantbackendapplication1.dining.dto.request.diningspacetypelocale.UpdateDiningSpaceTypeLocaleRequest;
import com.example.restaurantbackendapplication1.dining.model.entity.DiningSpaceTypeEntity;
import com.example.restaurantbackendapplication1.dining.model.entity.DiningSpaceTypeLocaleEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;

public interface DiningSpaceTypeLocaleService {
    SuccessResponse create(DiningSpaceTypeEntity diningSpaceTypeEntity, LocaleEntity localeEntity,
                           CreateDiningSpaceTypeLocaleRequest request);

    DiningSpaceTypeLocaleEntity getEntityById(Long diningSpaceTypeId, Long id);

    SuccessResponse update(DiningSpaceTypeLocaleEntity entity, UpdateDiningSpaceTypeLocaleRequest request);

    SuccessResponse delete(DiningSpaceTypeLocaleEntity entity);
}
