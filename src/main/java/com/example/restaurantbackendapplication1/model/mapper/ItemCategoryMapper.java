package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.itemcategory.CreateItemCategoryRequest;
import com.example.restaurantbackendapplication1.dto.request.itemcategory.UpdateItemCategoryRequest;
import com.example.restaurantbackendapplication1.model.dto.ItemCategoryDto;
import com.example.restaurantbackendapplication1.model.entity.ItemCategoryEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemCategoryLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemTypeEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class ItemCategoryMapper {

    public static ItemCategoryEntity fromRequest(CreateItemCategoryRequest request,
                                                 ItemTypeEntity itemTypeEntity,
                                                 Map<Long, LocaleEntity> localeEntityMap) {
        ItemCategoryEntity entity = new ItemCategoryEntity();
        entity.setItemTypeEntity(itemTypeEntity);
        entity.setCode(request.getCode());
        entity.setSortOrder(request.getSortOrder());

        if (request.getLocales() != null && !request.getLocales().isEmpty()) {
            Set<ItemCategoryLocaleEntity> locales = request.getLocales().stream()
                    .map(l -> ItemCategoryLocaleMapper.fromRequest(l, entity, localeEntityMap.get(l.getLocaleId())))
                    .collect(Collectors.toSet());
            entity.setItemCategoryLocaleEntities(locales);
        }
        return entity;
    }

    public static void update(ItemCategoryEntity entity, UpdateItemCategoryRequest request,
                              ItemTypeEntity itemTypeEntity) {
        entity.setItemTypeEntity(itemTypeEntity);
        entity.setCode(request.getCode());
        entity.setSortOrder(request.getSortOrder());
    }

    public static ItemCategoryDto toDto(ItemCategoryEntity entity) {
        ItemCategoryDto dto = new ItemCategoryDto();
        dto.setId(entity.getId());
        dto.setItemTypeId(entity.getItemTypeEntity().getId());
        dto.setCode(entity.getCode());
        dto.setSortOrder(entity.getSortOrder());
        return dto;
    }
}
