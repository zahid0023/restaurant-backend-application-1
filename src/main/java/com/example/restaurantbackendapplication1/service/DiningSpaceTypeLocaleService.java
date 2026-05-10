package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.diningspacetypelocale.CreateDiningSpaceTypeLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.diningspacetypelocale.UpdateDiningSpaceTypeLocaleRequest;
import com.example.restaurantbackendapplication1.dto.response.DiningSpaceTypeLocaleResponse;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceTypeEntity;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceTypeLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.projection.DiningSpaceTypeLocaleSummary;

public interface DiningSpaceTypeLocaleService {
    SuccessResponse create(DiningSpaceTypeEntity diningSpaceType, LocaleEntity locale,
                           CreateDiningSpaceTypeLocaleRequest request);

    DiningSpaceTypeLocaleEntity getEntityById(Long id, DiningSpaceTypeEntity diningSpaceType);

    DiningSpaceTypeLocaleResponse getById(Long id, DiningSpaceTypeEntity diningSpaceType);

    PaginatedResponse<DiningSpaceTypeLocaleSummary> getAll(DiningSpaceTypeEntity diningSpaceType,
                                                           PaginatedRequest request);

    SuccessResponse update(DiningSpaceTypeLocaleEntity entity, LocaleEntity locale,
                           UpdateDiningSpaceTypeLocaleRequest request);

    SuccessResponse delete(DiningSpaceTypeLocaleEntity entity);
}
