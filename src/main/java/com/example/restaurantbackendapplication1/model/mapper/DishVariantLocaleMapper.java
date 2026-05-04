package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.dishvariantlocale.DishVariantLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.dishvariantlocale.UpdateDishVariantLocaleRequest;
import com.example.restaurantbackendapplication1.model.dto.DishVariantLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.DishVariantEntity;
import com.example.restaurantbackendapplication1.model.entity.DishVariantLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DishVariantLocaleMapper {

    public static DishVariantLocaleEntity fromRequest(DishVariantLocaleRequest request,
                                                      DishVariantEntity dishVariantEntity,
                                                      LocaleEntity localeEntity) {
        DishVariantLocaleEntity entity = new DishVariantLocaleEntity();
        entity.setDishVariantEntity(dishVariantEntity);
        entity.setLocaleEntity(localeEntity);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setSortOrder(request.getSortOrder());
        return entity;
    }

    public static void update(DishVariantLocaleEntity entity,
                              UpdateDishVariantLocaleRequest request,
                              LocaleEntity localeEntity) {
        entity.setLocaleEntity(localeEntity);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setSortOrder(request.getSortOrder());
    }

    public static DishVariantLocaleDto toDto(DishVariantLocaleEntity entity) {
        DishVariantLocaleDto dto = new DishVariantLocaleDto();
        dto.setId(entity.getId());
        dto.setLocaleId(entity.getLocaleEntity().getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setSortOrder(entity.getSortOrder());
        return dto;
    }
}
