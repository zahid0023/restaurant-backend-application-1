package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.dishvariant.CreateDishVariantRequest;
import com.example.restaurantbackendapplication1.dto.request.dishvariant.UpdateDishVariantRequest;
import com.example.restaurantbackendapplication1.model.dto.DishVariantDto;
import com.example.restaurantbackendapplication1.model.entity.DishEntity;
import com.example.restaurantbackendapplication1.model.entity.DishVariantEntity;
import com.example.restaurantbackendapplication1.model.entity.DishVariantLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class DishVariantMapper {

    public static DishVariantEntity fromRequest(CreateDishVariantRequest request,
                                                DishEntity dishEntity,
                                                Map<Long, LocaleEntity> localeEntityMap) {
        DishVariantEntity entity = new DishVariantEntity();
        entity.setDishEntity(dishEntity);
        entity.setCode(request.getCode());
        entity.setSortOrder(request.getSortOrder());
        entity.setPrice(request.getPrice());
        entity.setIsDefault(request.getIsDefault());

        if (request.getLocales() != null && !request.getLocales().isEmpty()) {
            Set<DishVariantLocaleEntity> localeEntities = request.getLocales().stream()
                    .map(lr -> DishVariantLocaleMapper.fromRequest(lr, entity, localeEntityMap.get(lr.getLocaleId())))
                    .collect(Collectors.toSet());
            entity.setDishVariantLocaleEntities(localeEntities);
        }
        return entity;
    }

    public static void update(DishVariantEntity entity, UpdateDishVariantRequest request) {
        entity.setCode(request.getCode());
        entity.setSortOrder(request.getSortOrder());
        entity.setPrice(request.getPrice());
        entity.setIsDefault(request.getIsDefault());
    }

    public static DishVariantDto toDto(DishVariantEntity entity) {
        DishVariantDto dto = new DishVariantDto();
        dto.setId(entity.getId());
        dto.setDishId(entity.getDishEntity().getId());
        dto.setCode(entity.getCode());
        dto.setSortOrder(entity.getSortOrder());
        dto.setPrice(entity.getPrice());
        dto.setIsDefault(entity.getIsDefault());
        return dto;
    }
}
