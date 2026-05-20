package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.dishrecipe.CreateDishRecipeRequest;
import com.example.restaurantbackendapplication1.dto.request.dishrecipe.UpdateDishRecipeRequest;
import com.example.restaurantbackendapplication1.model.dto.DishRecipeDto;
import com.example.restaurantbackendapplication1.model.dto.DishRecipeIngredientDto;
import com.example.restaurantbackendapplication1.model.entity.DishRecipeEntity;
import com.example.restaurantbackendapplication1.model.entity.DishRecipeIngredientEntity;
import com.example.restaurantbackendapplication1.model.entity.DishVariantEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitEntity;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class DishRecipeMapper {

    public static DishRecipeEntity create(CreateDishRecipeRequest request,
                                          DishVariantEntity dishVariantEntity,
                                          Map<Long, ItemEntity> itemEntityMap,
                                          Map<Long, UnitEntity> unitEntityMap) {
        DishRecipeEntity entity = new DishRecipeEntity();
        entity.setDishVariantEntity(dishVariantEntity);
        entity.setCode(request.getCode());

        Set<DishRecipeIngredientEntity> ingredients = request.getIngredients().stream()
                .map(ir -> DishRecipeIngredientMapper.create(
                        ir, entity,
                        itemEntityMap.get(ir.getItemId()),
                        ir.getUnitId() != null ? unitEntityMap.get(ir.getUnitId()) : null))
                .collect(Collectors.toSet());
        entity.setDishRecipeIngredientEntities(ingredients);


        return entity;
    }

    public static void update(DishRecipeEntity entity, UpdateDishRecipeRequest request) {
        entity.setCode(request.getCode());
    }

    public static DishRecipeDto toDto(DishRecipeEntity entity) {
        List<DishRecipeIngredientDto> ingredients = entity.getDishRecipeIngredientEntities().stream()
                .map(DishRecipeIngredientMapper::toDto)
                .toList();
        return DishRecipeDto.builder()
                .id(entity.getId())
                .dishVariantId(entity.getDishVariantEntity().getId())
                .code(entity.getCode())
                .ingredients(ingredients)
                .build();
    }
}
