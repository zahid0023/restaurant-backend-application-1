package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.floorlocale.FloorLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.floorlocale.UpdateFloorLocaleRequest;
import com.example.restaurantbackendapplication1.model.dto.FloorLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.FloorEntity;
import com.example.restaurantbackendapplication1.model.entity.FloorLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FloorLocaleMapper {

    public static FloorLocaleEntity fromRequest(
            FloorLocaleRequest request,
            FloorEntity floorEntity,
            LocaleEntity localeEntity) {
        FloorLocaleEntity entity = new FloorLocaleEntity();
        entity.setFloorEntity(floorEntity);
        entity.setLocaleEntity(localeEntity);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setSortOrder(request.getSortOrder());
        return entity;
    }

    public static void update(
            FloorLocaleEntity entity,
            UpdateFloorLocaleRequest request,
            LocaleEntity localeEntity) {
        entity.setLocaleEntity(localeEntity);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setSortOrder(request.getSortOrder());
    }

    public static FloorLocaleDto toDto(FloorLocaleEntity entity) {
        FloorLocaleDto dto = new FloorLocaleDto();
        dto.setId(entity.getId());
        dto.setLocaleId(entity.getLocaleEntity().getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setSortOrder(entity.getSortOrder());
        return dto;
    }
}
