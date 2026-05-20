package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.dishvariantlocale.CreateDishVariantLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.dishvariantlocale.DishVariantLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.dishvariantlocale.UpdateDishVariantLocaleRequest;
import com.example.restaurantbackendapplication1.model.dto.DishVariantLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.DishVariantEntity;
import com.example.restaurantbackendapplication1.model.entity.DishVariantLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DishVariantLocaleMapper {

    public DishVariantLocaleEntity create(CreateDishVariantLocaleRequest request,
                                          DishVariantEntity dishVariantEntity,
                                          LocaleEntity localeEntity) {
        DishVariantLocaleEntity entity = new DishVariantLocaleEntity();
        entity.setDishVariantEntity(dishVariantEntity);
        entity.setLocaleEntity(localeEntity);
        applyCommonFields(entity, request);
        return entity;
    }

    public void update(DishVariantLocaleEntity entity, UpdateDishVariantLocaleRequest request) {
        applyCommonFields(entity, request);
    }

    private void applyCommonFields(DishVariantLocaleEntity entity, DishVariantLocaleRequest request) {
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setSortOrder(request.getSortOrder());
    }

    public DishVariantLocaleDto toDto(DishVariantLocaleEntity entity) {
        return DishVariantLocaleDto.builder()
                .id(entity.getId())
                .localeId(entity.getLocaleEntity().getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .sortOrder(entity.getSortOrder())
                .build();
    }
}
