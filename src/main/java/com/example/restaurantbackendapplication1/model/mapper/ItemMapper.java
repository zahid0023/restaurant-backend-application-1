package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.item.CreateItemRequest;
import com.example.restaurantbackendapplication1.dto.request.item.ItemRequest;
import com.example.restaurantbackendapplication1.dto.request.item.UpdateItemRequest;
import com.example.restaurantbackendapplication1.dto.request.itemlocale.CreateItemLocaleRequest;
import com.example.restaurantbackendapplication1.model.dto.ItemDto;
import com.example.restaurantbackendapplication1.model.dto.ItemLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitEntity;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class ItemMapper {

    public static ItemEntity fromRequest(CreateItemRequest request,
                                         UnitEntity unitEntity,
                                         Map<Long, LocaleEntity> localeEntityMap) {
        ItemEntity entity = new ItemEntity();
        entity.setCode(request.getCode());
        entity.setUnitEntity(unitEntity);
        entity.setSortOrder(request.getSortOrder());
        if (request.getLocales() != null) {
            entity.setItemLocaleEntities(mapLocales(request.getLocales(), entity, localeEntityMap));
        }
        return entity;
    }

    public static void update(ItemEntity entity, UpdateItemRequest request, UnitEntity unitEntity) {
        applyCommonFields(entity, request, unitEntity);
    }

    private static void applyCommonFields(ItemEntity entity, ItemRequest request, UnitEntity unitEntity) {
        entity.setUnitEntity(unitEntity);
        entity.setSortOrder(request.getSortOrder());
    }

    private static Set<ItemLocaleEntity> mapLocales(List<CreateItemLocaleRequest> locales,
                                                     ItemEntity entity,
                                                     Map<Long, LocaleEntity> localeEntityMap) {
        return locales.stream()
                .map(l -> ItemLocaleMapper.fromRequest(l, entity, localeEntityMap.get(l.getLocaleId())))
                .collect(Collectors.toSet());
    }

    public static ItemDto toDto(ItemEntity entity) {
        List<ItemLocaleDto> localeDtos = entity.getItemLocaleEntities().stream()
                .map(ItemLocaleMapper::toDto)
                .toList();

        return ItemDto.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .unitId(entity.getUnitEntity().getId())
                .sortOrder(entity.getSortOrder())
                .locales(localeDtos)
                .build();
    }
}
