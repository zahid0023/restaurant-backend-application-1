package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.itemcategorylocale.ItemCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.itemcategorylocale.UpdateItemCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.model.dto.ItemCategoryLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.ItemCategoryEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemCategoryLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ItemCategoryLocaleMapper {

    public static ItemCategoryLocaleEntity fromRequest(
            ItemCategoryLocaleRequest request,
            ItemCategoryEntity itemCategoryEntity,
            LocaleEntity localeEntity) {
        ItemCategoryLocaleEntity entity = new ItemCategoryLocaleEntity();
        entity.setItemCategoryEntity(itemCategoryEntity);
        entity.setLocaleEntity(localeEntity);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setSortOrder(request.getSortOrder());
        return entity;
    }

    public static void update(
            ItemCategoryLocaleEntity entity,
            UpdateItemCategoryLocaleRequest request,
            LocaleEntity locale) {
        entity.setLocaleEntity(locale);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setSortOrder(request.getSortOrder());
    }

    public static ItemCategoryLocaleDto toDto(ItemCategoryLocaleEntity entity) {
        ItemCategoryLocaleDto dto = new ItemCategoryLocaleDto();
        dto.setId(entity.getId());
        dto.setLocaleId(entity.getLocaleEntity().getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setSortOrder(entity.getSortOrder());
        return dto;
    }
}
