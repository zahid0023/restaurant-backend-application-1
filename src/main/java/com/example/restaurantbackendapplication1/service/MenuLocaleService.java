package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.menulocale.CreateMenuLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.menulocale.UpdateMenuLocaleRequest;
import com.example.restaurantbackendapplication1.dto.response.MenuLocaleResponse;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuLocaleEntity;
import com.example.restaurantbackendapplication1.model.projection.MenuLocaleSummary;

public interface MenuLocaleService {
    SuccessResponse create(MenuEntity menuEntity, LocaleEntity localeEntity, CreateMenuLocaleRequest request);

    MenuLocaleEntity getEntityById(Long id, MenuEntity menuEntity);

    MenuLocaleResponse getById(Long id, MenuEntity menuEntity);

    PaginatedResponse<MenuLocaleSummary> getAll(MenuEntity menuEntity, PaginatedRequest request);

    SuccessResponse update(MenuLocaleEntity entity, LocaleEntity localeEntity, UpdateMenuLocaleRequest request);

    SuccessResponse delete(MenuLocaleEntity entity);
}
