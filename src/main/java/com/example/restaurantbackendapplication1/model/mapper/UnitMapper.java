package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.unit.CreateUnitRequest;
import com.example.restaurantbackendapplication1.dto.request.unit.UpdateUnitRequest;
import com.example.restaurantbackendapplication1.model.dto.UnitDto;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitTypeEntity;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class UnitMapper {

    public static UnitEntity fromRequest(CreateUnitRequest request,
                                         UnitTypeEntity unitTypeEntity,
                                         Map<Long, LocaleEntity> localeEntityMap) {
        UnitEntity entity = new UnitEntity();
        entity.setUnitTypeEntity(unitTypeEntity);
        entity.setCode(request.getCode());
        entity.setIsBase(request.getIsBase());
        entity.setSortOrder(request.getSortOrder());

        if (request.getLocales() != null) {
            Set<UnitLocaleEntity> localeEntities = request.getLocales().stream()
                    .map(l -> UnitLocaleMapper.fromRequest(l, entity, localeEntityMap.get(l.getLocaleId())))
                    .collect(Collectors.toSet());
            entity.setUnitLocaleEntities(localeEntities);
        }
        return entity;
    }

    public static void update(UnitEntity entity,
                              UpdateUnitRequest request) {
        entity.setCode(request.getCode());
        entity.setIsBase(request.getIsBase());
        entity.setSortOrder(request.getSortOrder());
    }

    public static UnitDto toDto(UnitEntity entity) {
        UnitDto dto = new UnitDto();
        dto.setId(entity.getId());
        dto.setUnitTypeId(entity.getUnitTypeEntity().getId());
        dto.setCode(entity.getCode());
        dto.setIsBase(entity.getIsBase());
        dto.setSortOrder(entity.getSortOrder());
        return dto;
    }
}
