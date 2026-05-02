package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.menusection.CreateMenuSectionRequest;
import com.example.restaurantbackendapplication1.dto.request.menusection.UpdateMenuSectionRequest;
import com.example.restaurantbackendapplication1.dto.response.MenuSectionResponse;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuSectionEntity;
import com.example.restaurantbackendapplication1.model.projection.MenuSectionSummary;

import java.util.Map;

public interface MenuSectionService {
    SuccessResponse create(MenuEntity menuEntity, CreateMenuSectionRequest request,
                           Map<Long, LocaleEntity> localeEntityMap);

    MenuSectionEntity getEntityById(Long id, MenuEntity menuEntity);

    MenuSectionResponse getById(Long id, MenuEntity menuEntity);

    PaginatedResponse<MenuSectionSummary> getAll(MenuEntity menuEntity, PaginatedRequest request);

    SuccessResponse update(MenuSectionEntity entity, UpdateMenuSectionRequest request);

    SuccessResponse delete(MenuSectionEntity entity);
}
