package com.example.restaurantbackendapplication1.menu.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.menu.dto.request.menucategory.CreateMenuCategoryRequest;
import com.example.restaurantbackendapplication1.menu.dto.request.menucategory.UpdateMenuCategoryRequest;
import com.example.restaurantbackendapplication1.menu.dto.response.MenuCategoryResponse;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.menu.model.entity.MenuCategoryEntity;
import com.example.restaurantbackendapplication1.menu.model.entity.MenuTypeEntity;
import com.example.restaurantbackendapplication1.menu.model.projection.MenuCategorySummary;

import java.util.Map;

public interface MenuCategoryService {
    SuccessResponse create(CreateMenuCategoryRequest request,
                           MenuTypeEntity menuTypeEntity,
                           Map<Long, LocaleEntity> localeEntityMap);

    MenuCategoryEntity getEntityById(Long id);

    MenuCategoryResponse getById(Long id);

    PaginatedResponse<MenuCategorySummary> getAll(PaginatedRequest request);

    PaginatedResponse<MenuCategorySummary> getAll(Long menuTypeId, PaginatedRequest request);

    SuccessResponse update(MenuCategoryEntity entity,
                           UpdateMenuCategoryRequest request);

    SuccessResponse delete(Long id);
}
