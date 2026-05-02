package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.recipeitem.CreateRecipeItemRequest;
import com.example.restaurantbackendapplication1.dto.request.recipeitem.UpdateRecipeItemRequest;
import com.example.restaurantbackendapplication1.model.dto.RecipeItemDto;
import com.example.restaurantbackendapplication1.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.model.entity.RecipeEntity;
import com.example.restaurantbackendapplication1.model.entity.RecipeItemEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RecipeItemMapper {

    public static RecipeItemEntity fromRequest(CreateRecipeItemRequest request,
                                               RecipeEntity recipeEntity,
                                               ItemEntity itemEntity) {
        RecipeItemEntity entity = new RecipeItemEntity();
        entity.setRecipeEntity(recipeEntity);
        entity.setItemEntity(itemEntity);
        entity.setQuantity(request.getQuantity());
        return entity;
    }

    public static void update(RecipeItemEntity entity,
                              UpdateRecipeItemRequest request,
                              ItemEntity itemEntity) {
        entity.setItemEntity(itemEntity);
        entity.setQuantity(request.getQuantity());
    }

    public static RecipeItemDto toDto(RecipeItemEntity entity) {
        RecipeItemDto dto = new RecipeItemDto();
        dto.setId(entity.getId());
        dto.setRecipeId(entity.getRecipeEntity().getId());
        dto.setItemId(entity.getItemEntity().getId());
        dto.setQuantity(entity.getQuantity());
        return dto;
    }
}
