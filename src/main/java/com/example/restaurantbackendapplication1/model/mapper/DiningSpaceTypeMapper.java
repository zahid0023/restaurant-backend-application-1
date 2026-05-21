package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.diningspacetype.CreateDiningSpaceTypeRequest;
import com.example.restaurantbackendapplication1.dto.request.diningspacetype.DiningSpaceTypeRequest;
import com.example.restaurantbackendapplication1.dto.request.diningspacetype.UpdateDiningSpaceTypeRequest;
import com.example.restaurantbackendapplication1.dto.request.diningspacetypelocale.CreateDiningSpaceTypeLocaleRequest;
import com.example.restaurantbackendapplication1.model.dto.DiningSpaceTypeDto;
import com.example.restaurantbackendapplication1.model.dto.DiningSpaceTypeLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceTypeEntity;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceTypeLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class DiningSpaceTypeMapper {

    public DiningSpaceTypeEntity create(CreateDiningSpaceTypeRequest request,
                                        Map<Long, LocaleEntity> localeEntityMap) {
        DiningSpaceTypeEntity entity = new DiningSpaceTypeEntity();
        entity.setCode(request.getCode());
        applyCommonFields(entity, request);

        if (request.getLocales() != null) {
            Set<DiningSpaceTypeLocaleEntity> locales = request.getLocales().stream()
                    .map(l -> DiningSpaceTypeLocaleMapper.create(l, entity, localeEntityMap.get(l.getLocaleId())))
                    .collect(Collectors.toSet());
            entity.setDiningSpaceTypeLocaleEntities(locales);
        }
        return entity;
    }

    public void update(DiningSpaceTypeEntity entity, UpdateDiningSpaceTypeRequest request) {
        applyCommonFields(entity, request);
    }

    private void applyCommonFields(DiningSpaceTypeEntity entity, DiningSpaceTypeRequest request) {
        entity.setSortOrder(request.getSortOrder());
    }

    public DiningSpaceTypeDto toDto(DiningSpaceTypeEntity entity) {
        List<DiningSpaceTypeLocaleDto> locales = entity.getDiningSpaceTypeLocaleEntities().stream()
                .map(DiningSpaceTypeLocaleMapper::toDto)
                .toList();

        return DiningSpaceTypeDto.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .sortOrder(entity.getSortOrder())
                .locales(locales)
                .build();
    }
}
