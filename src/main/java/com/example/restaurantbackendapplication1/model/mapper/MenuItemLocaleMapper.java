package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.menuitemlocale.MenuItemLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.menuitemlocale.UpdateMenuItemLocaleRequest;
import com.example.restaurantbackendapplication1.model.dto.MenuItemLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuItemEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuItemLocaleEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MenuItemLocaleMapper {

    public static MenuItemLocaleEntity fromRequest(MenuItemLocaleRequest request,
                                                   MenuItemEntity menuItemEntity,
                                                   LocaleEntity localeEntity) {
        MenuItemLocaleEntity entity = new MenuItemLocaleEntity();
        entity.setMenuItemEntity(menuItemEntity);
        entity.setLocaleEntity(localeEntity);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setSortOrder(request.getSortOrder());
        return entity;
    }

    public static void update(MenuItemLocaleEntity entity,
                              UpdateMenuItemLocaleRequest request,
                              LocaleEntity localeEntity) {
        entity.setLocaleEntity(localeEntity);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setSortOrder(request.getSortOrder());
    }

    public static MenuItemLocaleDto toDto(MenuItemLocaleEntity entity) {
        MenuItemLocaleDto dto = new MenuItemLocaleDto();
        dto.setId(entity.getId());
        dto.setLocaleId(entity.getLocaleEntity().getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setSortOrder(entity.getSortOrder());
        return dto;
    }
}
