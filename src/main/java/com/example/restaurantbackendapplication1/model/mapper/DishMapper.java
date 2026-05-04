package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.dish.CreateDishRequest;
import com.example.restaurantbackendapplication1.dto.request.dish.UpdateDishRequest;
import com.example.restaurantbackendapplication1.model.dto.DishDto;
import com.example.restaurantbackendapplication1.model.entity.DishEntity;
import com.example.restaurantbackendapplication1.model.entity.DishesLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuCategoryEntity;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class DishMapper {

    public static DishEntity fromRequest(CreateDishRequest request,
                                         MenuCategoryEntity menuCategoryEntity,
                                         Map<Long, LocaleEntity> localeEntityMap) {
        DishEntity entity = new DishEntity();
        entity.setMenuCategoryEntity(menuCategoryEntity);
        entity.setCode(request.getCode());
        entity.setSortOrder(request.getSortOrder());
        entity.setIsAvailable(request.getIsAvailable());
        entity.setIsFeatured(request.getIsFeatured());
        entity.setIsVeg(request.getIsVeg());

        if (request.getLocales() != null && !request.getLocales().isEmpty()) {
            Set<DishesLocaleEntity> localeEntities = request.getLocales().stream()
                    .map(lr -> DishLocaleMapper.fromRequest(lr, entity, localeEntityMap.get(lr.getLocaleId())))
                    .collect(Collectors.toSet());
            entity.setDishesLocaleEntities(localeEntities);
        }
        return entity;
    }

    public static void update(DishEntity entity, UpdateDishRequest request) {
        entity.setCode(request.getCode());
        entity.setSortOrder(request.getSortOrder());
        entity.setIsAvailable(request.getIsAvailable());
        entity.setIsFeatured(request.getIsFeatured());
        entity.setIsVeg(request.getIsVeg());
    }

    public static DishDto toDto(DishEntity entity) {
        DishDto dto = new DishDto();
        dto.setId(entity.getId());
        dto.setMenuCategoryId(entity.getMenuCategoryEntity().getId());
        dto.setCode(entity.getCode());
        dto.setSortOrder(entity.getSortOrder());
        dto.setIsAvailable(entity.getIsAvailable());
        dto.setIsFeatured(entity.getIsFeatured());
        dto.setIsVeg(entity.getIsVeg());
        return dto;
    }
}
