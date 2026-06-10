package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.item.CreateItemRequest;
import com.example.restaurantbackendapplication1.dto.request.item.ItemRequest;
import com.example.restaurantbackendapplication1.dto.request.item.UpdateItemRequest;
import com.example.restaurantbackendapplication1.dto.request.itemlocale.CreateItemLocaleRequest;
import com.example.restaurantbackendapplication1.model.dto.*;
import com.example.restaurantbackendapplication1.model.entity.*;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class ItemMapper {

    public static ItemEntity fromRequest(CreateItemRequest request,
                                         ItemTypeEntity itemTypeEntity,
                                         UnitTypeEntity unitTypeEntity,
                                         Map<Long, LocaleEntity> localeEntityMap) {
        ItemEntity entity = new ItemEntity();
        entity.setCode(request.getCode());
        entity.setItemTypeEntity(itemTypeEntity);
        entity.setUnitTypeEntity(unitTypeEntity);
        entity.setSortOrder(request.getSortOrder());
        if (request.getLocales() != null) {
            entity.setItemLocaleEntities(mapLocales(request.getLocales(), entity, localeEntityMap));
        }
        return entity;
    }

    public static void update(ItemEntity entity, UpdateItemRequest request) {
        applyCommonFields(entity, request);
    }

    private static void applyCommonFields(ItemEntity entity, ItemRequest request) {
        entity.setSortOrder(request.getSortOrder());
    }

    private static Set<ItemLocaleEntity> mapLocales(List<CreateItemLocaleRequest> locales,
                                                    ItemEntity entity,
                                                    Map<Long, LocaleEntity> localeEntityMap) {
        return locales.stream()
                .map(l -> ItemLocaleMapper.fromRequest(l, entity, localeEntityMap.get(l.getLocaleId())))
                .collect(Collectors.toSet());
    }

    public static ItemSummaryDto toSummaryDto(ItemEntity entity) {
        List<ItemLocaleDto> localeDtos = entity.getItemLocaleEntities().stream()
                .map(ItemLocaleMapper::toDto)
                .toList();

        UnitTypeSummaryDto unitTypeSummaryDto = UnitTypeMapper.toSummaryDto(entity.getUnitTypeEntity());

        return ItemSummaryDto.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .sortOrder(entity.getSortOrder())
                .locales(localeDtos)
                .unitType(unitTypeSummaryDto)
                .build();
    }

    public static ItemDto toDto(ItemEntity entity) {
        List<ItemLocaleDto> localeDtos = entity.getItemLocaleEntities().stream()
                .map(ItemLocaleMapper::toDto)
                .toList();

        return ItemDto.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .sortOrder(entity.getSortOrder())
                .locales(localeDtos)
                .build();
    }
}
