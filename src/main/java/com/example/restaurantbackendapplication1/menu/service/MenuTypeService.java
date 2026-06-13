package com.example.restaurantbackendapplication1.menu.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.menu.dto.request.menutype.CreateMenuTypeRequest;
import com.example.restaurantbackendapplication1.menu.dto.request.menutype.UpdateMenuTypeRequest;
import com.example.restaurantbackendapplication1.menu.dto.response.MenuTypeResponse;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.menu.model.entity.MenuTypeEntity;
import com.example.restaurantbackendapplication1.menu.model.projection.MenuTypeFullSummary;
import com.example.restaurantbackendapplication1.menu.model.projection.MenuTypeSummary;
import com.example.restaurantbackendapplication1.menu.model.projection.MenuTypeWithCategoriesSummary;

import java.util.Map;

public interface MenuTypeService {
    SuccessResponse create(CreateMenuTypeRequest request,
                           Map<Long, LocaleEntity> localeEntityMap);

    MenuTypeEntity getEntityById(Long id);

    MenuTypeResponse getById(Long id);

    PaginatedResponse<MenuTypeSummary> getAll(PaginatedRequest request);

    PaginatedResponse<MenuTypeWithCategoriesSummary> getAllWithCategories(PaginatedRequest request);

    PaginatedResponse<MenuTypeFullSummary> getAllFull(PaginatedRequest request);

    SuccessResponse update(MenuTypeEntity entity, UpdateMenuTypeRequest request);

    SuccessResponse delete(MenuTypeEntity entity);
}
