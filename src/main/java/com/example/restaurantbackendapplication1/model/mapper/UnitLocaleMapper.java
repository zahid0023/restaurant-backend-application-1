package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.unitlocale.UnitLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.unitlocale.UpdateUnitLocaleRequest;
import com.example.restaurantbackendapplication1.model.dto.UnitLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitLocaleEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UnitLocaleMapper {

    public static UnitLocaleEntity fromRequest(
            UnitLocaleRequest request,
            UnitEntity unitEntity,
            LocaleEntity localeEntity) {
        UnitLocaleEntity entity = new UnitLocaleEntity();
        entity.setUnitEntity(unitEntity);
        entity.setLocaleEntity(localeEntity);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setSortOrder(request.getSortOrder());
        return entity;
    }

    public static void update(
            UnitLocaleEntity entity,
            UpdateUnitLocaleRequest request,
            LocaleEntity locale) {
        entity.setLocaleEntity(locale);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setSortOrder(request.getSortOrder());
    }

    public static UnitLocaleDto toDto(UnitLocaleEntity entity) {
        UnitLocaleDto dto = new UnitLocaleDto();
        dto.setId(entity.getId());
        dto.setLocaleId(entity.getLocaleEntity().getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setSortOrder(entity.getSortOrder());
        return dto;
    }
}
