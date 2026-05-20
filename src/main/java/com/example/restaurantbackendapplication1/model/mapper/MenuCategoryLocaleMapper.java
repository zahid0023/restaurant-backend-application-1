package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.menucategory.menucategorylocale.CreateMenuCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.menucategory.menucategorylocale.MenuCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.model.dto.MenuCategoryLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuCategoryEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuCategoryLocaleEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MenuCategoryLocaleMapper {

    public static MenuCategoryLocaleEntity create(CreateMenuCategoryLocaleRequest request,
                                                  MenuCategoryEntity menuCategoryEntity,
                                                  LocaleEntity localeEntity) {
        MenuCategoryLocaleEntity entity = new MenuCategoryLocaleEntity();
        entity.setMenuCategoryEntity(menuCategoryEntity);
        entity.setLocaleEntity(localeEntity);
        applyCommonFields(entity, request);
        return entity;
    }

    public static void update(MenuCategoryLocaleEntity entity, MenuCategoryLocaleRequest request) {
        applyCommonFields(entity, request);
    }

    private static void applyCommonFields(MenuCategoryLocaleEntity entity, MenuCategoryLocaleRequest request) {
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setSortOrder(request.getSortOrder());
    }

    public static MenuCategoryLocaleDto toDto(MenuCategoryLocaleEntity entity) {
        return MenuCategoryLocaleDto.builder()
                .id(entity.getId())
                .localeId(entity.getLocaleEntity().getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .sortOrder(entity.getSortOrder())
                .build();
    }
}
