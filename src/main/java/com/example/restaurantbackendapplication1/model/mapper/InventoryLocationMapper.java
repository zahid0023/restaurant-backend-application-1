package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.inventorylocation.CreateInventoryLocationRequest;
import com.example.restaurantbackendapplication1.dto.request.inventorylocation.UpdateInventoryLocationRequest;
import com.example.restaurantbackendapplication1.model.dto.InventoryLocationDto;
import com.example.restaurantbackendapplication1.model.entity.InventoryLocationEntity;
import com.example.restaurantbackendapplication1.model.entity.InventoryLocationLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class InventoryLocationMapper {

    public static InventoryLocationEntity fromRequest(CreateInventoryLocationRequest request,
                                                      Map<Long, LocaleEntity> localeEntityMap) {
        InventoryLocationEntity entity = new InventoryLocationEntity();
        entity.setCode(request.getCode());
        entity.setSortOrder(request.getSortOrder());

        if (request.getLocales() != null) {
            Set<InventoryLocationLocaleEntity> localeEntities = request.getLocales().stream()
                    .map(l -> InventoryLocationLocaleMapper.fromRequest(l, entity, localeEntityMap.get(l.getLocaleId())))
                    .collect(Collectors.toSet());
            entity.setInventoryLocationLocaleEntities(localeEntities);
        }
        return entity;
    }

    public static void update(InventoryLocationEntity entity, UpdateInventoryLocationRequest request) {
        entity.setCode(request.getCode());
        entity.setSortOrder(request.getSortOrder());
    }

    public static InventoryLocationDto toDto(InventoryLocationEntity entity) {
        return InventoryLocationDto.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .sortOrder(entity.getSortOrder())
                .build();
    }
}
