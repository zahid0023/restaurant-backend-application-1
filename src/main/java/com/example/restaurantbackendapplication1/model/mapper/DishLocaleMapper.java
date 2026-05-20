package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.dishlocale.CreateDishLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.dishlocale.DishLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.dishlocale.UpdateDishLocaleRequest;
import com.example.restaurantbackendapplication1.model.dto.DishLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.DishEntity;
import com.example.restaurantbackendapplication1.model.entity.DishesLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DishLocaleMapper {

    public DishesLocaleEntity create(CreateDishLocaleRequest request,
                                     DishEntity dishEntity,
                                     LocaleEntity localeEntity) {
        DishesLocaleEntity entity = new DishesLocaleEntity();
        entity.setDishEntity(dishEntity);
        entity.setLocaleEntity(localeEntity);
        applyCommonFields(entity, request);
        return entity;
    }

    public void update(DishesLocaleEntity entity, UpdateDishLocaleRequest request) {
        applyCommonFields(entity, request);  // locale is immutable — localeEntity not changed
    }

    private void applyCommonFields(DishesLocaleEntity entity, DishLocaleRequest request) {
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setSortOrder(request.getSortOrder());
    }

    public DishLocaleDto toDto(DishesLocaleEntity entity) {
        return DishLocaleDto.builder()
                .id(entity.getId())
                .localeId(entity.getLocaleEntity().getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .sortOrder(entity.getSortOrder())
                .build();
    }
}
