package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.menucategorylocale.CreateMenuCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.menucategorylocale.UpdateMenuCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.dto.response.MenuCategoryLocaleResponse;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuCategoryEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuCategoryLocaleEntity;
import com.example.restaurantbackendapplication1.model.projection.MenuCategoryLocaleSummary;

public interface MenuCategoryLocaleService {
    SuccessResponse create(MenuCategoryEntity menuCategoryEntity,
                           LocaleEntity localeEntity,
                           CreateMenuCategoryLocaleRequest request);

    MenuCategoryLocaleEntity getEntityById(Long menuCategoryId, Long id);

    MenuCategoryLocaleResponse getById(Long menuCategoryId, Long id);

    PaginatedResponse<MenuCategoryLocaleSummary> getAll(Long menuCategoryId,
                                                        PaginatedRequest request);

    SuccessResponse update(MenuCategoryLocaleEntity entity,
                           LocaleEntity localeEntity,
                           UpdateMenuCategoryLocaleRequest request);

    SuccessResponse delete(MenuCategoryLocaleEntity entity);
}
