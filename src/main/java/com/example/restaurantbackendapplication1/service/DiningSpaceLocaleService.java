package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.diningspacelocale.CreateDiningSpaceLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.diningspacelocale.UpdateDiningSpaceLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceEntity;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;

public interface DiningSpaceLocaleService {
    SuccessResponse create(DiningSpaceEntity diningSpace, LocaleEntity locale,
                           CreateDiningSpaceLocaleRequest request);

    DiningSpaceLocaleEntity getEntityById(Long diningSpaceId, Long id);

    SuccessResponse update(DiningSpaceLocaleEntity entity, UpdateDiningSpaceLocaleRequest request);

    SuccessResponse delete(DiningSpaceLocaleEntity entity);
}
