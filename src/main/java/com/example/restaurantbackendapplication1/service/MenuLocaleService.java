package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.menulocale.CreateMenuLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.menulocale.UpdateMenuLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuLocaleEntity;

public interface MenuLocaleService {
    SuccessResponse create(MenuEntity menuEntity, LocaleEntity localeEntity, CreateMenuLocaleRequest request);

    MenuLocaleEntity getEntityById(Long id, MenuEntity menuEntity);

    SuccessResponse update(MenuLocaleEntity entity, UpdateMenuLocaleRequest request);

    SuccessResponse delete(MenuLocaleEntity entity);
}
