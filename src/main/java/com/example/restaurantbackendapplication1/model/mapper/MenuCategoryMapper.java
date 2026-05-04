package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.menucategory.CreateMenuCategoryRequest;
import com.example.restaurantbackendapplication1.dto.request.menucategory.UpdateMenuCategoryRequest;
import com.example.restaurantbackendapplication1.model.dto.MenuCategoryDto;
import com.example.restaurantbackendapplication1.model.entity.*;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class MenuCategoryMapper {

    public static MenuCategoryEntity fromRequest(CreateMenuCategoryRequest request,
                                                 MenuEntity menuEntity,
                                                 Map<Long, LocaleEntity> localeEntityMap) {
        MenuCategoryEntity entity = new MenuCategoryEntity();
        entity.setMenuEntity(menuEntity);
        entity.setCode(request.getCode());
        entity.setSortOrder(request.getSortOrder());

        Set<MenuCategoryLocaleEntity> menuCategoriesLocaleEntities = request.getLocales().stream()
                .map(menuCategoryLocaleRequest -> MenuCategoryLocaleMapper.fromRequest(menuCategoryLocaleRequest, entity, localeEntityMap.get(menuCategoryLocaleRequest.getLocaleId())))
                .collect(Collectors.toSet());
        entity.setMenuCategoryLocaleEntities(menuCategoriesLocaleEntities);
        return entity;
    }

    public static void update(MenuCategoryEntity entity, UpdateMenuCategoryRequest request) {
        entity.setCode(request.getCode());
        entity.setSortOrder(request.getSortOrder());
    }

    public static MenuCategoryDto toDto(MenuCategoryEntity entity) {
        MenuCategoryDto dto = new MenuCategoryDto();
        dto.setId(entity.getId());
        dto.setMenuId(entity.getMenuEntity().getId());
        dto.setCode(entity.getCode());
        dto.setSortOrder(entity.getSortOrder());
        return dto;
    }
}
