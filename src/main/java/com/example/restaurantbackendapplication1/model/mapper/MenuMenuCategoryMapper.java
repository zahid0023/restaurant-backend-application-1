package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.model.dto.MenuMenuCategoryDto;
import com.example.restaurantbackendapplication1.model.entity.MenuCategoryEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuMenuCategoryEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MenuMenuCategoryMapper {

    public static MenuMenuCategoryEntity fromRequest(MenuEntity menuEntity, MenuCategoryEntity menuCategoryEntity) {
        MenuMenuCategoryEntity entity = new MenuMenuCategoryEntity();
        entity.setMenuEntity(menuEntity);
        entity.setMenuCategoryEntity(menuCategoryEntity);
        return entity;
    }

    public static MenuMenuCategoryDto toDto(MenuMenuCategoryEntity entity) {
        return MenuMenuCategoryDto.builder()
                .id(entity.getId())
                .menuId(entity.getMenuEntity().getId())
                .menuCategoryId(entity.getMenuCategoryEntity().getId())
                .build();
    }
}
