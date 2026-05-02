package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.recipe.CreateRecipeRequest;
import com.example.restaurantbackendapplication1.dto.request.recipe.UpdateRecipeRequest;
import com.example.restaurantbackendapplication1.model.dto.RecipeDto;
import com.example.restaurantbackendapplication1.model.entity.MenuItemEntity;
import com.example.restaurantbackendapplication1.model.entity.RecipeEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RecipeMapper {

    public static RecipeEntity fromRequest(CreateRecipeRequest request, MenuItemEntity menuItemEntity) {
        RecipeEntity entity = new RecipeEntity();
        entity.setMenuItemEntity(menuItemEntity);
        entity.setCode(request.getCode());
        return entity;
    }

    public static void update(RecipeEntity entity, UpdateRecipeRequest request) {
        entity.setCode(request.getCode());
    }

    public static RecipeDto toDto(RecipeEntity entity) {
        RecipeDto dto = new RecipeDto();
        dto.setId(entity.getId());
        dto.setMenuItemId(entity.getMenuItemEntity().getId());
        dto.setCode(entity.getCode());
        return dto;
    }
}
