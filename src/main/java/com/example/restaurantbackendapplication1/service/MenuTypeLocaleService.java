package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.menutypelocale.CreateMenuLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.menutypelocale.UpdateMenuLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuTypeEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuTypeLocaleEntity;

public interface MenuTypeLocaleService {
    SuccessResponse create(MenuTypeEntity menuTypeEntity, LocaleEntity localeEntity,
                           CreateMenuLocaleRequest request);

    MenuTypeLocaleEntity getEntityById(Long id,
                                       MenuTypeEntity menuTypeEntity);

    SuccessResponse update(MenuTypeLocaleEntity entity,
                           UpdateMenuLocaleRequest request);

    SuccessResponse delete(MenuTypeLocaleEntity entity);
}
