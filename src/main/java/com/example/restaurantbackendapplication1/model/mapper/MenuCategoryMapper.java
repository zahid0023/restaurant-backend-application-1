package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.menucategory.CreateMenuCategoryRequest;
import com.example.restaurantbackendapplication1.dto.request.menucategory.MenuCategoryRequest;
import com.example.restaurantbackendapplication1.dto.request.menucategory.UpdateMenuCategoryRequest;
import com.example.restaurantbackendapplication1.dto.request.menucategory.menucategorylocale.CreateMenuCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.model.dto.MenuCategoryDto;
import com.example.restaurantbackendapplication1.model.dto.MenuCategoryLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuCategoryEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuCategoryLocaleEntity;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class MenuCategoryMapper {

    public static MenuCategoryEntity create(CreateMenuCategoryRequest request,
                                            Map<Long, LocaleEntity> localeEntityMap) {
        MenuCategoryEntity entity = new MenuCategoryEntity();
        entity.setCode(request.getCode());
        applyCommonFields(entity, request);
        if (request.getLocales() != null) {
            entity.setMenuCategoryLocaleEntities(mapLocales(request.getLocales(), entity, localeEntityMap));
        }
        return entity;
    }

    public static void update(MenuCategoryEntity entity, UpdateMenuCategoryRequest request) {
        applyCommonFields(entity, request);
    }

    private static void applyCommonFields(MenuCategoryEntity entity, MenuCategoryRequest request) {
        entity.setSortOrder(request.getSortOrder());
    }

    private static Set<MenuCategoryLocaleEntity> mapLocales(List<CreateMenuCategoryLocaleRequest> locales,
                                                            MenuCategoryEntity entity,
                                                            Map<Long, LocaleEntity> localeEntityMap) {
        return locales.stream()
                .map(locale -> MenuCategoryLocaleMapper.create(locale, entity, localeEntityMap.get(locale.getLocaleId())))
                .collect(Collectors.toSet());
    }

    public static MenuCategoryDto toDto(MenuCategoryEntity entity) {
        List<MenuCategoryLocaleDto> localesDto = entity.getMenuCategoryLocaleEntities().stream()
                .map(MenuCategoryLocaleMapper::toDto)
                .toList();
        return MenuCategoryDto.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .sortOrder(entity.getSortOrder())
                .locales(localesDto)
                .build();
    }
}
