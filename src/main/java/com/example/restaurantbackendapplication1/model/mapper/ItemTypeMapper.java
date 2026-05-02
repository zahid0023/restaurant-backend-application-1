package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.itemtype.CreateItemTypeRequest;
import com.example.restaurantbackendapplication1.dto.request.itemtype.UpdateItemTypeRequest;
import com.example.restaurantbackendapplication1.model.dto.ItemTypeDto;
import com.example.restaurantbackendapplication1.model.entity.ItemTypeEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemTypeLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class ItemTypeMapper {

    public static ItemTypeEntity fromRequest(CreateItemTypeRequest request,
                                             Map<Long, LocaleEntity> localeEntityMap) {
        ItemTypeEntity entity = new ItemTypeEntity();
        entity.setCode(request.getCode());
        entity.setIsConsumable(request.getIsConsumable());
        entity.setSortOrder(request.getSortOrder());

        Set<ItemTypeLocaleEntity> itemTypeLocaleEntities = request.getLocales().stream()
                .map(itemTypeLocaleRequest -> ItemTypeLocaleMapper.fromRequest(itemTypeLocaleRequest, entity, localeEntityMap.get(itemTypeLocaleRequest.getLocaleId())))
                .collect(Collectors.toSet());
        entity.setItemTypeLocaleEntities(itemTypeLocaleEntities);
        return entity;
    }

    public static void update(ItemTypeEntity entity, UpdateItemTypeRequest request) {
        entity.setCode(request.getCode());
        entity.setIsConsumable(request.getIsConsumable());
        entity.setSortOrder(request.getSortOrder());
    }

    public static ItemTypeDto toDto(ItemTypeEntity entity) {
        ItemTypeDto dto = new ItemTypeDto();
        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setIsConsumable(entity.getIsConsumable());
        dto.setSortOrder(entity.getSortOrder());
        return dto;
    }
}
