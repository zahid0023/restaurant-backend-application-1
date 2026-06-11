package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.model.dto.MenuCategoryDishDto;
import com.example.restaurantbackendapplication1.model.entity.DishEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuCategoryDishEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuCategoryEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MenuCategoryDishMapper {

    public static MenuCategoryDishEntity create(MenuCategoryEntity menuCategoryEntity, DishEntity dishEntity) {
        MenuCategoryDishEntity entity = new MenuCategoryDishEntity();
        entity.setMenuCategoryEntity(menuCategoryEntity);
        entity.setDishEntity(dishEntity);
        return entity;
    }

    public static MenuCategoryDishDto toDto(MenuCategoryDishEntity entity) {
        return MenuCategoryDishDto.builder()
                .id(entity.getId())
                .menuCategoryId(entity.getMenuCategoryEntity().getId())
                .dish(DishMapper.toDto(entity.getDishEntity()))
                .build();
    }
}
