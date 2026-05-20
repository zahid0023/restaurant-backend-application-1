package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.model.entity.MenuCategoryEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuMenuCategoryEntity;
import com.example.restaurantbackendapplication1.model.projection.MenuCategorySummary;

public interface MenuMenuCategoryService {
    SuccessResponse assign(MenuEntity menuEntity, MenuCategoryEntity menuCategoryEntity);

    PaginatedResponse<MenuCategorySummary> getAllMenuCategories(Long menuId, PaginatedRequest request);

    MenuMenuCategoryEntity getAssignment(MenuEntity menuEntity, MenuCategoryEntity menuCategoryEntity);

    SuccessResponse unassign(MenuMenuCategoryEntity entity);
}
