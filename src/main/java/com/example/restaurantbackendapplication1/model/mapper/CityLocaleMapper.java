package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.citylocale.CityLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.citylocale.UpdateCityLocaleRequest;
import com.example.restaurantbackendapplication1.model.dto.CityLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.CityEntity;
import com.example.restaurantbackendapplication1.model.entity.CityLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CityLocaleMapper {

    public static CityLocaleEntity fromRequest(
            CityLocaleRequest request,
            CityEntity cityEntity,
            LocaleEntity localeEntity) {
        CityLocaleEntity entity = new CityLocaleEntity();
        entity.setCityEntity(cityEntity);
        entity.setLocaleEntity(localeEntity);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setSortOrder(request.getSortOrder());
        return entity;
    }

    public static void update(
            CityLocaleEntity entity,
            UpdateCityLocaleRequest request,
            LocaleEntity locale) {
        entity.setLocaleEntity(locale);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setSortOrder(request.getSortOrder());
    }

    public static CityLocaleDto toDto(CityLocaleEntity entity) {
        CityLocaleDto dto = new CityLocaleDto();
        dto.setId(entity.getId());
        dto.setLocaleId(entity.getLocaleEntity().getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setSortOrder(entity.getSortOrder());
        return dto;
    }
}
