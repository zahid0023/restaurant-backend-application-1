package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.diningspacelocale.CreateDiningSpaceLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.diningspacelocale.UpdateDiningSpaceLocaleRequest;
import com.example.restaurantbackendapplication1.dto.response.DiningSpaceLocaleResponse;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceEntity;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.projection.DiningSpaceLocaleSummary;

public interface DiningSpaceLocaleService {
    SuccessResponse create(DiningSpaceEntity diningSpace, LocaleEntity locale,
                           CreateDiningSpaceLocaleRequest request);

    DiningSpaceLocaleEntity getEntityById(Long id, DiningSpaceEntity diningSpace);

    DiningSpaceLocaleResponse getById(Long id, DiningSpaceEntity diningSpace);

    PaginatedResponse<DiningSpaceLocaleSummary> getAll(DiningSpaceEntity diningSpace, PaginatedRequest request);

    SuccessResponse update(DiningSpaceLocaleEntity entity, LocaleEntity locale,
                           UpdateDiningSpaceLocaleRequest request);

    SuccessResponse delete(DiningSpaceLocaleEntity entity);
}
