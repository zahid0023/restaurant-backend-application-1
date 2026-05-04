package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.menucategorylocale.MenuCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.menucategorylocale.UpdateMenuCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.model.dto.MenuCategoryLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.MenuCategoryEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuCategoryLocaleEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MenuCategoryLocaleMapper {

    public static MenuCategoryLocaleEntity fromRequest(
            MenuCategoryLocaleRequest request,
            MenuCategoryEntity menuCategoryEntity,
            LocaleEntity localeEntity) {
        MenuCategoryLocaleEntity entity = new MenuCategoryLocaleEntity();
        entity.setMenuCategoryEntity(menuCategoryEntity);
        entity.setLocaleEntity(localeEntity);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setSortOrder(request.getSortOrder());
        return entity;
    }

    public static void update(
            MenuCategoryLocaleEntity entity,
            UpdateMenuCategoryLocaleRequest request,
            LocaleEntity localeEntity) {
        entity.setLocaleEntity(localeEntity);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setSortOrder(request.getSortOrder());
    }

    public static MenuCategoryLocaleDto toDto(MenuCategoryLocaleEntity entity) {
        MenuCategoryLocaleDto dto = new MenuCategoryLocaleDto();
        dto.setId(entity.getId());
        dto.setLocaleId(entity.getLocaleEntity().getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setSortOrder(entity.getSortOrder());
        return dto;
    }
}
