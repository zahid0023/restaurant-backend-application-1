package com.example.restaurantbackendapplication1.item.model.mapper;

import com.example.restaurantbackendapplication1.item.dto.request.CreateItemRequest;
import com.example.restaurantbackendapplication1.item.dto.request.ItemRequest;
import com.example.restaurantbackendapplication1.item.dto.request.UpdateItemRequest;
import com.example.restaurantbackendapplication1.item.dto.request.itemlocale.CreateItemLocaleRequest;
import com.example.restaurantbackendapplication1.item.model.dto.ItemDto;
import com.example.restaurantbackendapplication1.item.model.dto.ItemLocaleDto;
import com.example.restaurantbackendapplication1.item.model.dto.ItemSummaryDto;
import com.example.restaurantbackendapplication1.item.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.item.model.entity.ItemLocaleEntity;
import com.example.restaurantbackendapplication1.item.model.entity.ItemTypeEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.unit.model.dto.UnitTypeSummaryDto;
import com.example.restaurantbackendapplication1.unit.model.entity.UnitTypeEntity;
import com.example.restaurantbackendapplication1.unit.model.mapper.UnitTypeMapper;
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
