package com.example.restaurantbackendapplication1.item.model.mapper;

import com.example.restaurantbackendapplication1.item.dto.request.itemtype.CreateItemTypeRequest;
import com.example.restaurantbackendapplication1.item.dto.request.itemtype.ItemTypeRequest;
import com.example.restaurantbackendapplication1.item.dto.request.itemtype.UpdateItemTypeRequest;
import com.example.restaurantbackendapplication1.item.dto.request.itemtypelocale.CreateItemTypeLocaleRequest;
import com.example.restaurantbackendapplication1.item.model.dto.ItemDto;
import com.example.restaurantbackendapplication1.item.model.dto.ItemTypeDto;
import com.example.restaurantbackendapplication1.item.model.dto.ItemTypeLocaleDto;
import com.example.restaurantbackendapplication1.item.model.entity.ItemTypeEntity;
import com.example.restaurantbackendapplication1.item.model.entity.ItemTypeLocaleEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class ItemTypeMapper {

    public ItemTypeEntity create(CreateItemTypeRequest request,
                                 Map<Long, LocaleEntity> localeEntityMap) {
        ItemTypeEntity entity = new ItemTypeEntity();
        entity.setCode(request.getCode());
        applyCommonFields(entity, request);
        entity.setItemTypeLocaleEntities(mapLocales(request.getLocales(), entity, localeEntityMap));
        return entity;
    }

    public void update(ItemTypeEntity entity, UpdateItemTypeRequest request) {
        applyCommonFields(entity, request);
    }

    private void applyCommonFields(ItemTypeEntity entity, ItemTypeRequest request) {
        entity.setIsConsumable(request.getIsConsumable());
        entity.setSortOrder(request.getSortOrder());
    }

    private Set<ItemTypeLocaleEntity> mapLocales(List<CreateItemTypeLocaleRequest> locales,
                                                 ItemTypeEntity entity,
                                                 Map<Long, LocaleEntity> localeEntityMap) {
        return locales.stream()
                .map(l -> ItemTypeLocaleMapper.create(l, entity, localeEntityMap.get(l.getLocaleId())))
                .collect(Collectors.toSet());
    }

    public ItemTypeDto toDto(ItemTypeEntity entity,
                             Boolean includeItems) {
        List<ItemTypeLocaleDto> locales = entity.getItemTypeLocaleEntities().stream()
                .map(ItemTypeLocaleMapper::toDto)
                .toList();

        List<ItemDto> items = includeItems ?
                entity.getItemEntities().stream()
                        .map(item -> ItemMapper.toDto(item, null, null))
                        .toList()
                : null;

        return ItemTypeDto.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .isConsumable(entity.getIsConsumable())
                .sortOrder(entity.getSortOrder())
                .locales(locales)
                .items(items)
                .build();
    }
}
