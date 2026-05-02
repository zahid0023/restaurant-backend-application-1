package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.unittypelocale.UnitTypeLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.unittypelocale.UpdateUnitTypeLocaleRequest;
import com.example.restaurantbackendapplication1.model.dto.UnitTypeLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitTypeEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitTypeLocaleEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UnitTypeLocaleMapper {

    public static UnitTypeLocaleEntity fromRequest(
            UnitTypeLocaleRequest request,
            UnitTypeEntity unitTypeEntity,
            LocaleEntity localeEntity) {
        UnitTypeLocaleEntity entity = new UnitTypeLocaleEntity();
        entity.setUnitTypeEntity(unitTypeEntity);
        entity.setLocaleEntity(localeEntity);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setSortOrder(request.getSortOrder());
        return entity;
    }

    public static void update(
            UnitTypeLocaleEntity entity,
            UpdateUnitTypeLocaleRequest request,
            LocaleEntity locale) {
        entity.setLocaleEntity(locale);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setSortOrder(request.getSortOrder());
    }

    public static UnitTypeLocaleDto toDto(UnitTypeLocaleEntity entity) {
        UnitTypeLocaleDto dto = new UnitTypeLocaleDto();
        dto.setId(entity.getId());
        dto.setLocaleId(entity.getLocaleEntity().getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setSortOrder(entity.getSortOrder());
        return dto;
    }
}
