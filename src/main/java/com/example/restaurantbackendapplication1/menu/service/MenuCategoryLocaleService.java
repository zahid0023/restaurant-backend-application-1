package com.example.restaurantbackendapplication1.menu.service;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.menu.dto.request.menucategorylocale.CreateMenuCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.menu.dto.request.menucategorylocale.UpdateMenuCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.menu.model.entity.MenuCategoryEntity;
import com.example.restaurantbackendapplication1.menu.model.entity.MenuCategoryLocaleEntity;

public interface MenuCategoryLocaleService {

    SuccessResponse create(MenuCategoryEntity menuCategoryEntity,
                           LocaleEntity localeEntity,
                           CreateMenuCategoryLocaleRequest request);

    MenuCategoryLocaleEntity getEntityById(Long menuCategoryId, Long id);

    SuccessResponse update(MenuCategoryLocaleEntity entity,
                           UpdateMenuCategoryLocaleRequest request);

    SuccessResponse delete(MenuCategoryLocaleEntity entity);
}
