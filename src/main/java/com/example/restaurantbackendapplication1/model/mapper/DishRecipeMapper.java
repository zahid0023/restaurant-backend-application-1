package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.dishrecipe.CreateDishRecipeRequest;
import com.example.restaurantbackendapplication1.dto.request.dishrecipe.UpdateDishRecipeRequest;
import com.example.restaurantbackendapplication1.model.dto.DishRecipeDto;
import com.example.restaurantbackendapplication1.model.entity.DishRecipeEntity;
import com.example.restaurantbackendapplication1.model.entity.DishVariantEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DishRecipeMapper {

    public static DishRecipeEntity fromRequest(CreateDishRecipeRequest request,
                                               DishVariantEntity dishVariantEntity) {
        DishRecipeEntity entity = new DishRecipeEntity();
        entity.setDishVariantEntity(dishVariantEntity);
        entity.setCode(request.getCode());
        return entity;
    }

    public static void update(DishRecipeEntity entity, UpdateDishRecipeRequest request) {
        entity.setCode(request.getCode());
    }

    public static DishRecipeDto toDto(DishRecipeEntity entity) {
        DishRecipeDto dto = new DishRecipeDto();
        dto.setId(entity.getId());
        dto.setDishVariantId(entity.getDishVariantEntity().getId());
        dto.setCode(entity.getCode());
        return dto;
    }
}
