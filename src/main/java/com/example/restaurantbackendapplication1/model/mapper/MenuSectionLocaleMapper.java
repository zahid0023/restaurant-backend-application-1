package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.menusectionlocale.MenuSectionLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.menusectionlocale.UpdateMenuSectionLocaleRequest;
import com.example.restaurantbackendapplication1.model.dto.MenuSectionLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuSectionEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuSectionLocaleEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MenuSectionLocaleMapper {

    public static MenuSectionLocaleEntity fromRequest(MenuSectionLocaleRequest request,
                                                      MenuSectionEntity menuSectionEntity,
                                                      LocaleEntity localeEntity) {
        MenuSectionLocaleEntity entity = new MenuSectionLocaleEntity();
        entity.setMenuSectionEntity(menuSectionEntity);
        entity.setLocaleEntity(localeEntity);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setSortOrder(request.getSortOrder());
        return entity;
    }

    public static void update(MenuSectionLocaleEntity entity,
                              UpdateMenuSectionLocaleRequest request,
                              LocaleEntity localeEntity) {
        entity.setLocaleEntity(localeEntity);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setSortOrder(request.getSortOrder());
    }

    public static MenuSectionLocaleDto toDto(MenuSectionLocaleEntity entity) {
        MenuSectionLocaleDto dto = new MenuSectionLocaleDto();
        dto.setId(entity.getId());
        dto.setLocaleId(entity.getLocaleEntity().getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setSortOrder(entity.getSortOrder());
        return dto;
    }
}
