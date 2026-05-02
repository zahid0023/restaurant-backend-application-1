package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.itemtypelocale.ItemTypeLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.itemtypelocale.UpdateItemTypeLocaleRequest;
import com.example.restaurantbackendapplication1.model.dto.ItemTypeLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.ItemTypeEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemTypeLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ItemTypeLocaleMapper {

    public static ItemTypeLocaleEntity fromRequest(
            ItemTypeLocaleRequest request,
            ItemTypeEntity itemTypeEntity,
            LocaleEntity localeEntity) {
        ItemTypeLocaleEntity entity = new ItemTypeLocaleEntity();
        entity.setItemTypeEntity(itemTypeEntity);
        entity.setLocaleEntity(localeEntity);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setSortOrder(request.getSortOrder());
        return entity;
    }

    public static void update(
            ItemTypeLocaleEntity entity,
            UpdateItemTypeLocaleRequest request,
            LocaleEntity locale) {
        entity.setLocaleEntity(locale);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setSortOrder(request.getSortOrder());
    }

    public static ItemTypeLocaleDto toDto(ItemTypeLocaleEntity entity) {
        ItemTypeLocaleDto dto = new ItemTypeLocaleDto();
        dto.setId(entity.getId());
        dto.setLocaleId(entity.getLocaleEntity().getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setSortOrder(entity.getSortOrder());
        return dto;
    }
}
