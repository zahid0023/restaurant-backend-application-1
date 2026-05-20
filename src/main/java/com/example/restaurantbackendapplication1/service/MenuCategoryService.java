package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.menucategory.CreateMenuCategoryRequest;
import com.example.restaurantbackendapplication1.dto.request.menucategory.UpdateMenuCategoryRequest;
import com.example.restaurantbackendapplication1.dto.response.MenuCategoryResponse;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuCategoryEntity;
import com.example.restaurantbackendapplication1.model.projection.MenuCategorySummary;

import java.util.Map;

public interface MenuCategoryService {
    SuccessResponse create(CreateMenuCategoryRequest request,
                           Map<Long, LocaleEntity> localeEntityMap);

    MenuCategoryEntity getEntityById(Long id);

    MenuCategoryResponse getById(Long id);

    PaginatedResponse<MenuCategorySummary> getAll(PaginatedRequest request);

    SuccessResponse update(MenuCategoryEntity entity, UpdateMenuCategoryRequest request);

    SuccessResponse delete(Long id);
}
