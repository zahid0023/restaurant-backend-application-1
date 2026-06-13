package com.example.restaurantbackendapplication1.locale.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.locale.dto.request.CreateLocaleRequest;
import com.example.restaurantbackendapplication1.locale.dto.request.UpdateLocaleRequest;
import com.example.restaurantbackendapplication1.locale.dto.response.LocaleResponse;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.locale.model.projection.LocaleSummary;

import java.util.List;
import java.util.Set;

public interface LocaleService {
    SuccessResponse create(CreateLocaleRequest request);

    LocaleEntity getEntityById(Long id);

    LocaleResponse getById(Long id);

    PaginatedResponse<LocaleSummary> getAll(PaginatedRequest request);

    SuccessResponse update(LocaleEntity entity, UpdateLocaleRequest request);

    SuccessResponse delete(Long id);

    List<LocaleEntity> getAll(Set<Long> ids);
}
