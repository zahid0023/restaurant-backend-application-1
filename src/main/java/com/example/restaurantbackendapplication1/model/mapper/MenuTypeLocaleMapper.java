package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.menutypelocale.CreateMenuLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.menutypelocale.UpdateMenuLocaleRequest;
import com.example.restaurantbackendapplication1.model.dto.MenuTypeLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuTypeEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuTypeLocaleEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MenuTypeLocaleMapper {

    public static MenuTypeLocaleEntity fromRequest(CreateMenuLocaleRequest request,
                                                   MenuTypeEntity menuTypeEntity,
                                                   LocaleEntity localeEntity) {
        MenuTypeLocaleEntity entity = new MenuTypeLocaleEntity();
        entity.setMenuTypeEntity(menuTypeEntity);
        entity.setLocaleEntity(localeEntity);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setSortOrder(request.getSortOrder());
        return entity;
    }

    public static void update(MenuTypeLocaleEntity entity, UpdateMenuLocaleRequest request) {
        entity.setName(request.getName());
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setSortOrder(request.getSortOrder());
    }

    public static MenuTypeLocaleDto toDto(MenuTypeLocaleEntity entity) {
        return MenuTypeLocaleDto.builder()
                .id(entity.getId())
                .localeId(entity.getLocaleEntity().getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .sortOrder(entity.getSortOrder())
                .build();
    }
}
