package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.unittype.CreateUnitTypeRequest;
import com.example.restaurantbackendapplication1.dto.request.unittype.UpdateUnitTypeRequest;
import com.example.restaurantbackendapplication1.model.dto.UnitTypeDto;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitTypeEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitTypeLocaleEntity;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class UnitTypeMapper {

    public static UnitTypeEntity fromRequest(CreateUnitTypeRequest request,
                                             Map<Long, LocaleEntity> localeEntityMap) {
        UnitTypeEntity entity = new UnitTypeEntity();
        entity.setCode(request.getCode());
        entity.setSortOrder(request.getSortOrder());

        if (request.getLocales() != null) {
            Set<UnitTypeLocaleEntity> localeEntities = request.getLocales().stream()
                    .map(l -> UnitTypeLocaleMapper.fromRequest(l, entity, localeEntityMap.get(l.getLocaleId())))
                    .collect(Collectors.toSet());
            entity.setUnitTypeLocaleEntities(localeEntities);
        }
        return entity;
    }

    public static void update(UnitTypeEntity entity, UpdateUnitTypeRequest request) {
        entity.setCode(request.getCode());
        entity.setSortOrder(request.getSortOrder());
    }

    public static UnitTypeDto toDto(UnitTypeEntity entity) {
        UnitTypeDto dto = new UnitTypeDto();
        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setSortOrder(entity.getSortOrder());
        return dto;
    }
}
