package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.menucategory.menucategorylocale.CreateMenuCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.menucategory.menucategorylocale.UpdateMenuCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuCategoryEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuCategoryLocaleEntity;

public interface MenuCategoryLocaleService {

    SuccessResponse create(MenuCategoryEntity menuCategoryEntity,
                           LocaleEntity localeEntity,
                           CreateMenuCategoryLocaleRequest request);

    MenuCategoryLocaleEntity getEntityById(Long menuCategoryId, Long id);

    SuccessResponse update(MenuCategoryLocaleEntity entity,
                           UpdateMenuCategoryLocaleRequest request);

    SuccessResponse delete(MenuCategoryLocaleEntity entity);
}
