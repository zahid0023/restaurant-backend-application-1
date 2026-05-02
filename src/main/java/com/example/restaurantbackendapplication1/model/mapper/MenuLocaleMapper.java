package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.menulocale.MenuLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.menulocale.UpdateMenuLocaleRequest;
import com.example.restaurantbackendapplication1.model.dto.MenuLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuLocaleEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MenuLocaleMapper {

    public static MenuLocaleEntity fromRequest(MenuLocaleRequest request,
                                               MenuEntity menuEntity,
                                               LocaleEntity localeEntity) {
        MenuLocaleEntity entity = new MenuLocaleEntity();
        entity.setMenuEntity(menuEntity);
        entity.setLocaleEntity(localeEntity);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setSortOrder(request.getSortOrder());
        return entity;
    }

    public static void update(MenuLocaleEntity entity,
                              UpdateMenuLocaleRequest request,
                              LocaleEntity localeEntity) {
        entity.setLocaleEntity(localeEntity);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setSortOrder(request.getSortOrder());
    }

    public static MenuLocaleDto toDto(MenuLocaleEntity entity) {
        MenuLocaleDto dto = new MenuLocaleDto();
        dto.setId(entity.getId());
        dto.setLocaleId(entity.getLocaleEntity().getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setSortOrder(entity.getSortOrder());
        return dto;
    }
}
