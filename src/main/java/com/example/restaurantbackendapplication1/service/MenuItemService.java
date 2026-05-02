package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.menuitem.CreateMenuItemRequest;
import com.example.restaurantbackendapplication1.dto.request.menuitem.UpdateMenuItemRequest;
import com.example.restaurantbackendapplication1.dto.response.MenuItemResponse;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuItemEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuSectionEntity;
import com.example.restaurantbackendapplication1.model.projection.MenuItemSummary;

import java.util.Map;

public interface MenuItemService {
    SuccessResponse create(MenuSectionEntity menuSectionEntity, CreateMenuItemRequest request,
                           Map<Long, LocaleEntity> localeEntityMap);

    MenuItemEntity getEntityById(Long id, MenuSectionEntity menuSectionEntity);

    MenuItemResponse getById(Long id, MenuSectionEntity menuSectionEntity);

    PaginatedResponse<MenuItemSummary> getAll(MenuSectionEntity menuSectionEntity, PaginatedRequest request);

    SuccessResponse update(MenuItemEntity entity, UpdateMenuItemRequest request);

    SuccessResponse delete(MenuItemEntity entity);
}
