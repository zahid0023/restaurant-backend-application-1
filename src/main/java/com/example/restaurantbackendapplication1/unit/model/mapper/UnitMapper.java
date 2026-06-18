package com.example.restaurantbackendapplication1.unit.model.mapper;

import com.example.restaurantbackendapplication1.unit.dto.request.CreateUnitRequest;
import com.example.restaurantbackendapplication1.unit.dto.request.UnitRequest;
import com.example.restaurantbackendapplication1.unit.dto.request.UpdateUnitRequest;
import com.example.restaurantbackendapplication1.unit.dto.request.unitlocale.CreateUnitLocaleRequest;
import com.example.restaurantbackendapplication1.unit.model.dto.UnitDto;
import com.example.restaurantbackendapplication1.unit.model.dto.UnitLocaleDto;
import com.example.restaurantbackendapplication1.unit.model.dto.UnitTypeDto;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.unit.model.entity.UnitEntity;
import com.example.restaurantbackendapplication1.unit.model.entity.UnitLocaleEntity;
import com.example.restaurantbackendapplication1.unit.model.entity.UnitTypeEntity;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class UnitMapper {

    public UnitEntity create(CreateUnitRequest request,
                             UnitTypeEntity unitTypeEntity,
                             Map<Long, LocaleEntity> localeEntityMap) {
        UnitEntity entity = new UnitEntity();
        entity.setUnitTypeEntity(unitTypeEntity);
        entity.setCode(request.getCode());
        applyCommonFields(entity, request);
        if (request.getLocales() != null) {
            entity.setUnitLocaleEntities(mapLocales(request.getLocales(), entity, localeEntityMap));
        }
        return entity;
    }

    public void update(UnitEntity entity, UpdateUnitRequest request) {
        applyCommonFields(entity, request);
    }

    private void applyCommonFields(UnitEntity entity, UnitRequest request) {
        entity.setIsBase(request.getIsBase());
        entity.setSortOrder(request.getSortOrder());
    }

    private Set<UnitLocaleEntity> mapLocales(List<CreateUnitLocaleRequest> locales,
                                             UnitEntity entity,
                                             Map<Long, LocaleEntity> localeEntityMap) {
        return locales.stream()
                .map(l -> UnitLocaleMapper.create(l, entity, localeEntityMap.get(l.getLocaleId())))
                .collect(Collectors.toSet());
    }

    public UnitDto toDto(UnitEntity entity) {
        return buildDto(entity, null);
    }

    public UnitDto toDtoWithUnitType(UnitEntity entity) {
        return buildDto(entity, UnitTypeMapper.toDto(entity.getUnitTypeEntity()));
    }

    private UnitDto buildDto(UnitEntity entity, UnitTypeDto unitType) {
        List<UnitLocaleDto> localeDtos = entity.getUnitLocaleEntities().stream()
                .map(UnitLocaleMapper::toDto)
                .toList();

        return UnitDto.builder()
                .id(entity.getId())
                .unitType(unitType)
                .code(entity.getCode())
                .isBase(entity.getIsBase())
                .sortOrder(entity.getSortOrder())
                .locales(localeDtos)
                .build();
    }
}
