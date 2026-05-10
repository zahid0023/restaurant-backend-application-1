package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.diningspacetype.CreateDiningSpaceTypeRequest;
import com.example.restaurantbackendapplication1.dto.request.diningspacetype.UpdateDiningSpaceTypeRequest;
import com.example.restaurantbackendapplication1.model.dto.DiningSpaceTypeDto;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceTypeEntity;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceTypeLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class DiningSpaceTypeMapper {

    public static DiningSpaceTypeEntity fromRequest(CreateDiningSpaceTypeRequest request,
                                                    Map<Long, LocaleEntity> localeEntityMap) {
        DiningSpaceTypeEntity entity = new DiningSpaceTypeEntity();
        entity.setCode(request.getCode());
        entity.setSortOrder(request.getSortOrder());

        Set<DiningSpaceTypeLocaleEntity> locales = request.getLocales().stream()
                .map(localeRequest -> DiningSpaceTypeLocaleMapper.fromRequest(
                        localeRequest, entity, localeEntityMap.get(localeRequest.getLocaleId())))
                .collect(Collectors.toSet());
        entity.setDiningSpaceTypeLocaleEntities(locales);
        return entity;
    }

    public static void update(DiningSpaceTypeEntity entity, UpdateDiningSpaceTypeRequest request) {
        entity.setCode(request.getCode());
        entity.setSortOrder(request.getSortOrder());
    }

    public static DiningSpaceTypeDto toDto(DiningSpaceTypeEntity entity) {
        DiningSpaceTypeDto dto = new DiningSpaceTypeDto();
        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setSortOrder(entity.getSortOrder());
        return dto;
    }
}
