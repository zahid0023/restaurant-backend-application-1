package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.menu.CreateMenuRequest;
import com.example.restaurantbackendapplication1.dto.request.menu.UpdateMenuRequest;
import com.example.restaurantbackendapplication1.dto.response.MenuResponse;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuEntity;
import com.example.restaurantbackendapplication1.model.projection.MenuSummary;

import java.util.Map;

public interface MenuService {
    SuccessResponse create(CreateMenuRequest request, Map<Long, LocaleEntity> localeEntityMap);

    MenuEntity getEntityById(Long id);

    MenuResponse getById(Long id);

    PaginatedResponse<MenuSummary> getAll(PaginatedRequest request);

    SuccessResponse update(MenuEntity entity, UpdateMenuRequest request);

    SuccessResponse delete(Long id);
}
