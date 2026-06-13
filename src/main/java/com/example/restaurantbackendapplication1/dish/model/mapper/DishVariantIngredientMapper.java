package com.example.restaurantbackendapplication1.dish.model.mapper;

import com.example.restaurantbackendapplication1.dish.dto.request.dishvariantingredient.CreateDishVariantIngredientRequest;
import com.example.restaurantbackendapplication1.dish.dto.request.dishvariantingredient.DishVariantIngredientRequest;
import com.example.restaurantbackendapplication1.dish.dto.request.dishvariantingredient.UpdateDishVariantIngredientRequest;
import com.example.restaurantbackendapplication1.dish.model.dto.DishVariantIngredientDto;
import com.example.restaurantbackendapplication1.dish.model.entity.DishVariantEntity;
import com.example.restaurantbackendapplication1.dish.model.entity.DishVariantIngredientEntity;
import com.example.restaurantbackendapplication1.item.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.unit.model.entity.UnitEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DishVariantIngredientMapper {

    public DishVariantIngredientEntity create(CreateDishVariantIngredientRequest request,
                                              DishVariantEntity dishVariantEntity,
                                              ItemEntity itemEntity,
                                              UnitEntity unitEntity) {
        DishVariantIngredientEntity entity = new DishVariantIngredientEntity();
        entity.setDishVariantEntity(dishVariantEntity);
        entity.setItemEntity(itemEntity);
        applyCommonFields(entity, request, unitEntity);
        return entity;
    }

    public void update(DishVariantIngredientEntity entity,
                       UpdateDishVariantIngredientRequest request,
                       UnitEntity unitEntity) {
        applyCommonFields(entity, request, unitEntity);
    }

    private void applyCommonFields(DishVariantIngredientEntity entity,
                                   DishVariantIngredientRequest request,
                                   UnitEntity unitEntity) {
        entity.setQuantity(request.getQuantity());
        entity.setUnitEntity(unitEntity);
        entity.setSortOrder(request.getSortOrder());
    }

    public DishVariantIngredientDto toDto(DishVariantIngredientEntity entity) {
        return DishVariantIngredientDto.builder()
                .id(entity.getId())
                .dishVariantId(entity.getDishVariantEntity().getId())
                .itemId(entity.getItemEntity().getId())
                .quantity(entity.getQuantity())
                .unitId(entity.getUnitEntity().getId())
                .sortOrder(entity.getSortOrder())
                .build();
    }
}
