package com.example.restaurantbackendapplication1.dining.model.mapper;

import com.example.restaurantbackendapplication1.dining.dto.request.floor.CreateFloorRequest;
import com.example.restaurantbackendapplication1.dining.dto.request.floor.FloorRequest;
import com.example.restaurantbackendapplication1.dining.dto.request.floor.UpdateFloorRequest;
import com.example.restaurantbackendapplication1.dining.dto.request.floorlocale.CreateFloorLocaleRequest;
import com.example.restaurantbackendapplication1.dining.model.dto.FloorDto;
import com.example.restaurantbackendapplication1.dining.model.dto.FloorLocaleDto;
import com.example.restaurantbackendapplication1.dining.model.entity.FloorEntity;
import com.example.restaurantbackendapplication1.dining.model.entity.FloorLocaleEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class FloorMapper {

    public FloorEntity create(CreateFloorRequest request, Map<Long, LocaleEntity> localeEntityMap) {
        FloorEntity entity = new FloorEntity();
        entity.setCode(request.getCode());
        applyCommonFields(entity, request);

        if (request.getLocales() != null) {
            Set<FloorLocaleEntity> localeEntities = request.getLocales().stream()
                    .map(l -> FloorLocaleMapper.create(l, entity, localeEntityMap.get(l.getLocaleId())))
                    .collect(Collectors.toSet());
            entity.setFloorLocaleEntities(localeEntities);
        }
        return entity;
    }

    public void update(FloorEntity entity, UpdateFloorRequest request) {
        applyCommonFields(entity, request);
    }

    private void applyCommonFields(FloorEntity entity, FloorRequest request) {
        entity.setSortOrder(request.getSortOrder());
    }

    public FloorDto toDto(FloorEntity entity) {
        List<FloorLocaleDto> locales = entity.getFloorLocaleEntities().stream()
                .map(FloorLocaleMapper::toDto)
                .toList();

        return FloorDto.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .sortOrder(entity.getSortOrder())
                .locales(locales)
                .build();
    }
}
