package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.item.CreateItemRequest;
import com.example.restaurantbackendapplication1.dto.request.item.UpdateItemRequest;
import com.example.restaurantbackendapplication1.model.dto.ItemDto;
import com.example.restaurantbackendapplication1.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitEntity;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class ItemMapper {

    public static ItemEntity fromRequest(CreateItemRequest request,
                                         UnitEntity unitEntity,
                                         Map<Long, LocaleEntity> localeEntityMap) {
        ItemEntity entity = new ItemEntity();
        entity.setUnitEntity(unitEntity);
        entity.setSortOrder(request.getSortOrder());

        if (request.getLocales() != null) {
            Set<ItemLocaleEntity> localeEntities = request.getLocales().stream()
                    .map(l -> ItemLocaleMapper.fromRequest(l, entity, localeEntityMap.get(l.getLocaleId())))
                    .collect(Collectors.toSet());
            entity.setItemLocaleEntities(localeEntities);
        }
        return entity;
    }

    public static void update(ItemEntity entity,
                              UpdateItemRequest request,
                              UnitEntity unitEntity) {
        entity.setUnitEntity(unitEntity);
        entity.setSortOrder(request.getSortOrder());
    }

    public static ItemDto toDto(ItemEntity entity) {
        ItemDto dto = new ItemDto();
        dto.setId(entity.getId());
        dto.setUnitId(entity.getUnitEntity().getId());
        dto.setSortOrder(entity.getSortOrder());
        return dto;
    }
}
