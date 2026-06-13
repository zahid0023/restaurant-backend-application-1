package com.example.restaurantbackendapplication1.item.model.mapper;

import com.example.restaurantbackendapplication1.item.dto.request.itemcategorylocale.ItemCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.item.dto.request.itemcategorylocale.UpdateItemCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.item.model.dto.ItemCategoryLocaleDto;
import com.example.restaurantbackendapplication1.item.model.entity.ItemCategoryEntity;
import com.example.restaurantbackendapplication1.item.model.entity.ItemCategoryLocaleEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;

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
            UpdateItemCategoryLocaleRequest request) {
        entity.setName(request.getName());
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setSortOrder(request.getSortOrder());
    }

    public static ItemCategoryLocaleDto toDto(ItemCategoryLocaleEntity entity) {
        return ItemCategoryLocaleDto.builder()
                .id(entity.getId())
                .localeId(entity.getLocaleEntity().getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .sortOrder(entity.getSortOrder())
                .build();
    }
}
