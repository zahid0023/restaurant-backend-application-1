package com.example.restaurantbackendapplication1.dish.model.mapper;

import com.example.restaurantbackendapplication1.dish.dto.request.dishvariant.CreateDishVariantRequest;
import com.example.restaurantbackendapplication1.dish.dto.request.dishvariant.DishVariantRequest;
import com.example.restaurantbackendapplication1.dish.dto.request.dishvariant.UpdateDishVariantRequest;
import com.example.restaurantbackendapplication1.dish.model.dto.DishVariantDto;
import com.example.restaurantbackendapplication1.dish.model.dto.DishVariantIngredientDto;
import com.example.restaurantbackendapplication1.dish.model.dto.DishVariantLocaleDto;
import com.example.restaurantbackendapplication1.dish.model.entity.DishEntity;
import com.example.restaurantbackendapplication1.dish.model.entity.DishVariantEntity;
import com.example.restaurantbackendapplication1.dish.model.entity.DishVariantIngredientEntity;
import com.example.restaurantbackendapplication1.dish.model.entity.DishVariantLocaleEntity;
import com.example.restaurantbackendapplication1.item.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.unit.model.entity.UnitEntity;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class DishVariantMapper {

    public DishVariantEntity create(CreateDishVariantRequest request,
                                    DishEntity dishEntity,
                                    Map<Long, LocaleEntity> localeEntityMap,
                                    Map<Long, ItemEntity> itemEntityMap,
                                    Map<Long, UnitEntity> unitEntityMap) {
        DishVariantEntity entity = new DishVariantEntity();
        entity.setDishEntity(dishEntity);
        entity.setCode(request.getCode());
        applyCommonFields(entity, request);

        if (request.getLocales() != null) {
            Set<DishVariantLocaleEntity> localeEntities = request.getLocales().stream()
                    .map(lr -> DishVariantLocaleMapper.create(lr, entity, localeEntityMap.get(lr.getLocaleId())))
                    .collect(Collectors.toSet());
            entity.setDishVariantLocaleEntities(localeEntities);
        }

        if (request.getIngredients() != null) {
            Set<DishVariantIngredientEntity> ingredientEntities = request.getIngredients().stream()
                    .map(ir -> DishVariantIngredientMapper.create(
                            ir, entity,
                            itemEntityMap.get(ir.getItemId()),
                            unitEntityMap.get(ir.getUnitId())))
                    .collect(Collectors.toSet());
            entity.setDishVariantIngredientEntities(ingredientEntities);
        }

        return entity;
    }

    public void update(DishVariantEntity entity, UpdateDishVariantRequest request) {
        applyCommonFields(entity, request);
    }

    private void applyCommonFields(DishVariantEntity entity, DishVariantRequest request) {
        entity.setSortOrder(request.getSortOrder());
        entity.setPrice(request.getPrice());
        entity.setIsDefault(request.getIsDefault());
        entity.setIsVeg(request.getIsVeg());
    }

    public DishVariantDto toDto(DishVariantEntity entity) {
        List<DishVariantLocaleDto> locales = entity.getDishVariantLocaleEntities().stream()
                .map(DishVariantLocaleMapper::toDto)
                .toList();

        List<DishVariantIngredientDto> ingredients = entity.getDishVariantIngredientEntities().stream()
                .filter(i -> Boolean.TRUE.equals(i.getIsActive()) && Boolean.FALSE.equals(i.getIsDeleted()))
                .map(DishVariantIngredientMapper::toDto)
                .toList();

        return DishVariantDto.builder()
                .id(entity.getId())
                .dishId(entity.getDishEntity().getId())
                .code(entity.getCode())
                .sortOrder(entity.getSortOrder())
                .price(entity.getPrice())
                .isDefault(entity.getIsDefault())
                .isVeg(entity.getIsVeg())
                .locales(locales)
                .ingredients(ingredients)
                .build();
    }
}
