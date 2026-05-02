package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.itemlocale.ItemLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.itemlocale.UpdateItemLocaleRequest;
import com.example.restaurantbackendapplication1.model.dto.ItemLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ItemLocaleMapper {

    public static ItemLocaleEntity fromRequest(
            ItemLocaleRequest request,
            ItemEntity itemEntity,
            LocaleEntity localeEntity) {
        ItemLocaleEntity entity = new ItemLocaleEntity();
        entity.setItemEntity(itemEntity);
        entity.setLocaleEntity(localeEntity);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setSortOrder(request.getSortOrder());
        return entity;
    }

    public static void update(
            ItemLocaleEntity entity,
            UpdateItemLocaleRequest request,
            LocaleEntity locale) {
        entity.setLocaleEntity(locale);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setSortOrder(request.getSortOrder());
    }

    public static ItemLocaleDto toDto(ItemLocaleEntity entity) {
        ItemLocaleDto dto = new ItemLocaleDto();
        dto.setId(entity.getId());
        dto.setLocaleId(entity.getLocaleEntity().getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setSortOrder(entity.getSortOrder());
        return dto;
    }
}
