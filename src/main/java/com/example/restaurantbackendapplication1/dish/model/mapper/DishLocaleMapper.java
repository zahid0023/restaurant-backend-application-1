package com.example.restaurantbackendapplication1.dish.model.mapper;

import com.example.restaurantbackendapplication1.dish.dto.request.dishlocale.CreateDishLocaleRequest;
import com.example.restaurantbackendapplication1.dish.dto.request.dishlocale.DishLocaleRequest;
import com.example.restaurantbackendapplication1.dish.dto.request.dishlocale.UpdateDishLocaleRequest;
import com.example.restaurantbackendapplication1.dish.model.dto.DishLocaleDto;
import com.example.restaurantbackendapplication1.dish.model.entity.DishEntity;
import com.example.restaurantbackendapplication1.dish.model.entity.DishLocaleEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DishLocaleMapper {

    public DishLocaleEntity create(CreateDishLocaleRequest request,
                                   DishEntity dishEntity,
                                   LocaleEntity localeEntity) {
        DishLocaleEntity entity = new DishLocaleEntity();
        entity.setDishEntity(dishEntity);
        entity.setLocaleEntity(localeEntity);
        applyCommonFields(entity, request);
        return entity;
    }

    public void update(DishLocaleEntity entity, UpdateDishLocaleRequest request) {
        applyCommonFields(entity, request);  // locale is immutable — localeEntity not changed
    }

    private void applyCommonFields(DishLocaleEntity entity, DishLocaleRequest request) {
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setSortOrder(request.getSortOrder());
    }

    public DishLocaleDto toDto(DishLocaleEntity entity) {
        return DishLocaleDto.builder()
                .id(entity.getId())
                .localeId(entity.getLocaleEntity().getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .sortOrder(entity.getSortOrder())
                .build();
    }
}
