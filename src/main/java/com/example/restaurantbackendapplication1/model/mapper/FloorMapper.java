package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.floor.CreateFloorRequest;
import com.example.restaurantbackendapplication1.dto.request.floor.UpdateFloorRequest;
import com.example.restaurantbackendapplication1.model.dto.FloorDto;
import com.example.restaurantbackendapplication1.model.entity.FloorEntity;
import com.example.restaurantbackendapplication1.model.entity.FloorLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class FloorMapper {

    public static FloorEntity fromRequest(CreateFloorRequest request,
                                          Map<Long, LocaleEntity> localeEntityMap) {
        FloorEntity entity = new FloorEntity();
        entity.setCode(request.getCode());
        entity.setSortOrder(request.getSortOrder());

        if (request.getLocales() != null) {
            Set<FloorLocaleEntity> localeEntities = request.getLocales().stream()
                    .map(l -> FloorLocaleMapper.fromRequest(l, entity, localeEntityMap.get(l.getLocaleId())))
                    .collect(Collectors.toSet());
            entity.setFloorLocaleEntities(localeEntities);
        }
        return entity;
    }

    public static void update(FloorEntity entity, UpdateFloorRequest request) {
        entity.setCode(request.getCode());
        entity.setSortOrder(request.getSortOrder());
    }

    public static FloorDto toDto(FloorEntity entity) {
        return FloorDto.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .sortOrder(entity.getSortOrder())
                .build();
    }
}
