package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.unittype.CreateUnitTypeRequest;
import com.example.restaurantbackendapplication1.dto.request.unittype.UnitTypeRequest;
import com.example.restaurantbackendapplication1.dto.request.unittype.UpdateUnitTypeRequest;
import com.example.restaurantbackendapplication1.model.dto.UnitTypeDto;
import com.example.restaurantbackendapplication1.model.dto.UnitTypeLocaleDto;
import com.example.restaurantbackendapplication1.model.dto.UnitTypeSummaryDto;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitTypeEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitTypeLocaleEntity;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class UnitTypeMapper {

    public UnitTypeEntity create(CreateUnitTypeRequest request, Map<Long, LocaleEntity> localeEntityMap) {
        UnitTypeEntity entity = new UnitTypeEntity();
        entity.setCode(request.getCode());
        applyCommonFields(entity, request);

        if (request.getLocales() != null) {
            Set<UnitTypeLocaleEntity> localeEntities = request.getLocales().stream()
                    .map(l -> UnitTypeLocaleMapper.create(l, entity, localeEntityMap.get(l.getLocaleId())))
                    .collect(Collectors.toSet());
            entity.setUnitTypeLocaleEntities(localeEntities);
        }
        return entity;
    }

    public void update(UnitTypeEntity entity, UpdateUnitTypeRequest request) {
        applyCommonFields(entity, request);
    }

    private void applyCommonFields(UnitTypeEntity entity, UnitTypeRequest request) {
        entity.setSortOrder(request.getSortOrder());
    }

    public UnitTypeSummaryDto toSummaryDto(UnitTypeEntity entity) {
        List<UnitTypeLocaleDto> locales = entity.getUnitTypeLocaleEntities().stream()
                .map(UnitTypeLocaleMapper::toDto)
                .collect(Collectors.toList());

        return UnitTypeSummaryDto.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .sortOrder(entity.getSortOrder())
                .locales(locales)
                .build();
    }

    public UnitTypeDto toDto(UnitTypeEntity entity) {
        List<UnitTypeLocaleDto> locales = entity.getUnitTypeLocaleEntities().stream()
                .map(UnitTypeLocaleMapper::toDto)
                .collect(Collectors.toList());

        return UnitTypeDto.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .sortOrder(entity.getSortOrder())
                .locales(locales)
                .build();
    }
}
