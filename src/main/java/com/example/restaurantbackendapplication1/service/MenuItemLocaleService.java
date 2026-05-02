package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.menuitemlocale.CreateMenuItemLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.menuitemlocale.UpdateMenuItemLocaleRequest;
import com.example.restaurantbackendapplication1.dto.response.MenuItemLocaleResponse;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuItemEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuItemLocaleEntity;
import com.example.restaurantbackendapplication1.model.projection.MenuItemLocaleSummary;

public interface MenuItemLocaleService {
    SuccessResponse create(MenuItemEntity menuItemEntity, LocaleEntity localeEntity,
                           CreateMenuItemLocaleRequest request);

    MenuItemLocaleEntity getEntityById(Long id, MenuItemEntity menuItemEntity);

    MenuItemLocaleResponse getById(Long id, MenuItemEntity menuItemEntity);

    PaginatedResponse<MenuItemLocaleSummary> getAll(MenuItemEntity menuItemEntity, PaginatedRequest request);

    SuccessResponse update(MenuItemLocaleEntity entity, LocaleEntity localeEntity,
                           UpdateMenuItemLocaleRequest request);

    SuccessResponse delete(MenuItemLocaleEntity entity);
}
