package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.dish.CreateDishRequest;
import com.example.restaurantbackendapplication1.dto.request.dish.DishRequest;
import com.example.restaurantbackendapplication1.dto.request.dish.UpdateDishRequest;
import com.example.restaurantbackendapplication1.model.dto.DishDto;
import com.example.restaurantbackendapplication1.model.dto.DishLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.DishEntity;
import com.example.restaurantbackendapplication1.model.entity.DishVariantEntity;
import com.example.restaurantbackendapplication1.model.entity.DishesLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuCategoryEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitEntity;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class DishMapper {

    public DishEntity create(CreateDishRequest request,
                             MenuCategoryEntity menuCategoryEntity,
                             Map<Long, LocaleEntity> localeEntityMap,
                             Map<Long, ItemEntity> itemEntityMap,
                             Map<Long, UnitEntity> unitEntityMap) {
        DishEntity entity = new DishEntity();
        entity.setMenuCategoryEntity(menuCategoryEntity);
        entity.setCode(request.getCode());  // immutable — only set on create
        applyCommonFields(entity, request);

        Set<DishesLocaleEntity> localeEntities = request.getLocales().stream()
                .map(lr -> DishLocaleMapper.create(lr, entity, localeEntityMap.get(lr.getLocaleId())))
                .collect(Collectors.toSet());
        entity.setDishesLocaleEntities(localeEntities);


        Set<DishVariantEntity> variantEntities = request.getVariants().stream()
                .map(vr -> DishVariantMapper.create(vr, entity, localeEntityMap, itemEntityMap, unitEntityMap))
                .collect(Collectors.toSet());
        entity.setDishVariantEntities(variantEntities);

        return entity;
    }

    public void update(DishEntity entity, UpdateDishRequest request) {
        applyCommonFields(entity, request);  // does NOT touch code
    }

    private void applyCommonFields(DishEntity entity, DishRequest request) {
        entity.setSortOrder(request.getSortOrder());
        entity.setIsVeg(request.getIsVeg());
    }

    public DishDto toDto(DishEntity entity) {
        List<DishLocaleDto> locales = entity.getDishesLocaleEntities().stream()
                .map(DishLocaleMapper::toDto)
                .toList();

        return DishDto.builder()
                .id(entity.getId())
                .menuCategoryId(entity.getMenuCategoryEntity().getId())
                .code(entity.getCode())
                .sortOrder(entity.getSortOrder())
                .isVeg(entity.getIsVeg())
                .locales(locales)
                .build();
    }
}
