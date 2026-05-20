package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.dishrecipe.CreateDishRecipeRequest;
import com.example.restaurantbackendapplication1.dto.request.dishvariant.CreateDishVariantRequest;
import com.example.restaurantbackendapplication1.dto.request.dishvariant.DishVariantRequest;
import com.example.restaurantbackendapplication1.dto.request.dishvariant.UpdateDishVariantRequest;
import com.example.restaurantbackendapplication1.dto.request.dishvariantlocale.CreateDishVariantLocaleRequest;
import com.example.restaurantbackendapplication1.model.dto.DishRecipeDto;
import com.example.restaurantbackendapplication1.model.dto.DishVariantDto;
import com.example.restaurantbackendapplication1.model.dto.DishVariantLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.DishEntity;
import com.example.restaurantbackendapplication1.model.entity.DishRecipeEntity;
import com.example.restaurantbackendapplication1.model.entity.DishVariantEntity;
import com.example.restaurantbackendapplication1.model.entity.DishVariantLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitEntity;
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

        entity.setDishVariantLocaleEntities(mapLocales(request.getLocales(), entity, localeEntityMap));

        entity.setDishRecipeEntities(Set.of(mapRecipe(request.getRecipe(), entity, itemEntityMap, unitEntityMap)));

        return entity;
    }

    public void update(DishVariantEntity entity, UpdateDishVariantRequest request) {
        applyCommonFields(entity, request);
    }

    private void applyCommonFields(DishVariantEntity entity, DishVariantRequest request) {
        entity.setSortOrder(request.getSortOrder());
        entity.setPrice(request.getPrice());
        entity.setIsDefault(request.getIsDefault());
        entity.setIsAvailable(request.getIsAvailable());
        entity.setIsFeatured(request.getIsFeatured());
    }

    private Set<DishVariantLocaleEntity> mapLocales(List<CreateDishVariantLocaleRequest> locales,
                                                    DishVariantEntity entity,
                                                    Map<Long, LocaleEntity> localeEntityMap) {
        return locales.stream()
                .map(locale -> DishVariantLocaleMapper.create(locale, entity, localeEntityMap.get(locale.getLocaleId())))
                .collect(Collectors.toSet());
    }

    private DishRecipeEntity mapRecipe(CreateDishRecipeRequest recipe,
                                       DishVariantEntity entity,
                                       Map<Long, ItemEntity> itemEntityMap,
                                       Map<Long, UnitEntity> unitEntityMap) {
        return DishRecipeMapper.create(recipe, entity, itemEntityMap, unitEntityMap);
    }

    public DishVariantDto toDto(DishVariantEntity entity) {
        List<DishVariantLocaleDto> locales = entity.getDishVariantLocaleEntities().stream()
                .map(DishVariantLocaleMapper::toDto)
                .toList();
        DishRecipeDto recipe = entity.getDishRecipeEntities().stream()
                .findFirst()
                .map(DishRecipeMapper::toDto)
                .orElse(null);
        return DishVariantDto.builder()
                .id(entity.getId())
                .dishId(entity.getDishEntity().getId())
                .code(entity.getCode())
                .sortOrder(entity.getSortOrder())
                .price(entity.getPrice())
                .isDefault(entity.getIsDefault())
                .isAvailable(entity.getIsAvailable())
                .isFeatured(entity.getIsFeatured())
                .locales(locales)
                .recipe(recipe)
                .build();
    }
}
