package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.dish.CreateDishRequest;
import com.example.restaurantbackendapplication1.dto.request.dish.DishRequest;
import com.example.restaurantbackendapplication1.dto.request.dish.UpdateDishRequest;
import com.example.restaurantbackendapplication1.model.dto.DishDto;
import com.example.restaurantbackendapplication1.model.dto.DishLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.DishEntity;
import com.example.restaurantbackendapplication1.model.entity.DishLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class DishMapper {

    public DishEntity create(CreateDishRequest request,
                             Map<Long, LocaleEntity> localeEntityMap) {
        DishEntity entity = new DishEntity();
        entity.setCode(request.getCode());
        applyCommonFields(entity, request);

        if (request.getLocales() != null) {
            Set<DishLocaleEntity> localeEntities = request.getLocales().stream()
                    .map(lr -> DishLocaleMapper.create(lr, entity, localeEntityMap.get(lr.getLocaleId())))
                    .collect(Collectors.toSet());
            entity.setDishesLocaleEntities(localeEntities);
        }

        return entity;
    }

    public void update(DishEntity entity, UpdateDishRequest request) {
        applyCommonFields(entity, request);
    }

    private void applyCommonFields(DishEntity entity, DishRequest request) {
        entity.setSortOrder(request.getSortOrder());
    }

    public DishDto toDto(DishEntity entity) {
        List<DishLocaleDto> locales = entity.getDishesLocaleEntities().stream()
                .map(DishLocaleMapper::toDto)
                .toList();

        return DishDto.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .sortOrder(entity.getSortOrder())
                .locales(locales)
                .build();
    }
}
