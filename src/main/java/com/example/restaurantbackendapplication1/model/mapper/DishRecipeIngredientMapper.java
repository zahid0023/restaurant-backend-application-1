package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.dishrecipeingredient.DishRecipeIngredientRequest;
import com.example.restaurantbackendapplication1.dto.request.dishrecipeingredient.UpdateDishRecipeIngredientRequest;
import com.example.restaurantbackendapplication1.model.dto.DishRecipeIngredientDto;
import com.example.restaurantbackendapplication1.model.entity.DishRecipeEntity;
import com.example.restaurantbackendapplication1.model.entity.DishRecipeIngredientEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DishRecipeIngredientMapper {

    public static DishRecipeIngredientEntity create(DishRecipeIngredientRequest request,
                                                         DishRecipeEntity dishRecipeEntity,
                                                         ItemEntity itemEntity,
                                                         UnitEntity unitEntity) {
        DishRecipeIngredientEntity entity = new DishRecipeIngredientEntity();
        entity.setDishRecipeEntity(dishRecipeEntity);
        entity.setItemEntity(itemEntity);
        entity.setQuantity(request.getQuantity());
        entity.setUnitEntity(unitEntity);
        return entity;
    }

    public static void update(DishRecipeIngredientEntity entity,
                              UpdateDishRecipeIngredientRequest request,
                              ItemEntity itemEntity,
                              UnitEntity unitEntity) {
        entity.setItemEntity(itemEntity);
        entity.setQuantity(request.getQuantity());
        entity.setUnitEntity(unitEntity);
    }

    public static DishRecipeIngredientDto toDto(DishRecipeIngredientEntity entity) {
        return DishRecipeIngredientDto.builder()
                .id(entity.getId())
                .dishRecipeId(entity.getDishRecipeEntity().getId())
                .itemId(entity.getItemEntity().getId())
                .quantity(entity.getQuantity())
                .unitId(entity.getUnitEntity() != null ? entity.getUnitEntity().getId() : null)
                .build();
    }
}
