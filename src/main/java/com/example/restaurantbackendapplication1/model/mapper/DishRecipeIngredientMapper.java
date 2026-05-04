package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.dishrecipeingredient.DishRecipeIngredientRequest;
import com.example.restaurantbackendapplication1.dto.request.dishrecipeingredient.UpdateDishRecipeIngredientRequest;
import com.example.restaurantbackendapplication1.model.dto.DishRecipeIngredientDto;
import com.example.restaurantbackendapplication1.model.entity.DishRecipeEntity;
import com.example.restaurantbackendapplication1.model.entity.DishRecipeIngredientEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DishRecipeIngredientMapper {

    public static DishRecipeIngredientEntity fromRequest(DishRecipeIngredientRequest request,
                                                         DishRecipeEntity dishRecipeEntity,
                                                         ItemEntity itemEntity) {
        DishRecipeIngredientEntity entity = new DishRecipeIngredientEntity();
        entity.setDishRecipeEntity(dishRecipeEntity);
        entity.setItemEntity(itemEntity);
        entity.setQuantity(request.getQuantity());
        return entity;
    }

    public static void update(DishRecipeIngredientEntity entity,
                              UpdateDishRecipeIngredientRequest request,
                              ItemEntity itemEntity) {
        entity.setItemEntity(itemEntity);
        entity.setQuantity(request.getQuantity());
    }

    public static DishRecipeIngredientDto toDto(DishRecipeIngredientEntity entity) {
        DishRecipeIngredientDto dto = new DishRecipeIngredientDto();
        dto.setId(entity.getId());
        dto.setDishRecipeId(entity.getDishRecipeEntity().getId());
        dto.setItemId(entity.getItemEntity().getId());
        dto.setQuantity(entity.getQuantity());
        return dto;
    }
}
