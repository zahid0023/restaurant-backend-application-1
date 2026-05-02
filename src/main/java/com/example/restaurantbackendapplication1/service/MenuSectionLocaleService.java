package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.menusectionlocale.CreateMenuSectionLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.menusectionlocale.UpdateMenuSectionLocaleRequest;
import com.example.restaurantbackendapplication1.dto.response.MenuSectionLocaleResponse;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuSectionEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuSectionLocaleEntity;
import com.example.restaurantbackendapplication1.model.projection.MenuSectionLocaleSummary;

public interface MenuSectionLocaleService {
    SuccessResponse create(MenuSectionEntity menuSectionEntity, LocaleEntity localeEntity,
                           CreateMenuSectionLocaleRequest request);

    MenuSectionLocaleEntity getEntityById(Long id, MenuSectionEntity menuSectionEntity);

    MenuSectionLocaleResponse getById(Long id, MenuSectionEntity menuSectionEntity);

    PaginatedResponse<MenuSectionLocaleSummary> getAll(MenuSectionEntity menuSectionEntity, PaginatedRequest request);

    SuccessResponse update(MenuSectionLocaleEntity entity, LocaleEntity localeEntity,
                           UpdateMenuSectionLocaleRequest request);

    SuccessResponse delete(MenuSectionLocaleEntity entity);
}
