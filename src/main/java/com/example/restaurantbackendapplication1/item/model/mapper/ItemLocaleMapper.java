package com.example.restaurantbackendapplication1.item.model.mapper;

import com.example.restaurantbackendapplication1.item.dto.request.itemlocale.CreateItemLocaleRequest;
import com.example.restaurantbackendapplication1.item.dto.request.itemlocale.ItemLocaleRequest;
import com.example.restaurantbackendapplication1.item.dto.request.itemlocale.UpdateItemLocaleRequest;
import com.example.restaurantbackendapplication1.item.model.dto.ItemLocaleDto;
import com.example.restaurantbackendapplication1.item.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.item.model.entity.ItemLocaleEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ItemLocaleMapper {

    public static ItemLocaleEntity fromRequest(CreateItemLocaleRequest request,
                                               ItemEntity itemEntity,
                                               LocaleEntity localeEntity) {
        ItemLocaleEntity entity = new ItemLocaleEntity();
        entity.setItemEntity(itemEntity);
        entity.setLocaleEntity(localeEntity);
        applyCommonFields(entity, request);
        return entity;
    }

    public static void update(ItemLocaleEntity entity, UpdateItemLocaleRequest request) {
        applyCommonFields(entity, request);
    }

    private static void applyCommonFields(ItemLocaleEntity entity, ItemLocaleRequest request) {
        entity.setName(request.getName());
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setSortOrder(request.getSortOrder());
    }

    public static ItemLocaleDto toDto(ItemLocaleEntity entity) {
        return ItemLocaleDto.builder()
                .id(entity.getId())
                .localeId(entity.getLocaleEntity().getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .sortOrder(entity.getSortOrder())
                .build();
    }
}
