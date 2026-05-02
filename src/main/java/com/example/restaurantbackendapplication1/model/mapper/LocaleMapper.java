package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.locale.CreateLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.locale.UpdateLocaleRequest;
import com.example.restaurantbackendapplication1.model.dto.LocaleDto;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LocaleMapper {

    public static LocaleEntity fromRequest(CreateLocaleRequest request) {
        LocaleEntity entity = new LocaleEntity();
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setSortOrder(request.getSortOrder());
        return entity;
    }

    public static void update(LocaleEntity entity, UpdateLocaleRequest request) {
        entity.setCode(request.getCode());
        entity.setName(request.getName());
        entity.setSortOrder(request.getSortOrder());
    }

    public static LocaleDto toDto(LocaleEntity entity) {
        LocaleDto dto = new LocaleDto();
        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setName(entity.getName());
        dto.setSortOrder(entity.getSortOrder());
        return dto;
    }
}
