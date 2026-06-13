package com.example.restaurantbackendapplication1.item.model.mapper;

import com.example.restaurantbackendapplication1.item.dto.request.itemtypelocale.CreateItemTypeLocaleRequest;
import com.example.restaurantbackendapplication1.item.dto.request.itemtypelocale.ItemTypeLocaleRequest;
import com.example.restaurantbackendapplication1.item.dto.request.itemtypelocale.UpdateItemTypeLocaleRequest;
import com.example.restaurantbackendapplication1.item.model.dto.ItemTypeLocaleDto;
import com.example.restaurantbackendapplication1.item.model.entity.ItemTypeEntity;
import com.example.restaurantbackendapplication1.item.model.entity.ItemTypeLocaleEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ItemTypeLocaleMapper {

    public ItemTypeLocaleEntity create(CreateItemTypeLocaleRequest request,
                                       ItemTypeEntity itemTypeEntity,
                                       LocaleEntity localeEntity) {
        ItemTypeLocaleEntity entity = new ItemTypeLocaleEntity();
        entity.setItemTypeEntity(itemTypeEntity);
        entity.setLocaleEntity(localeEntity);
        applyCommonFields(entity, request);
        return entity;
    }

    public void update(ItemTypeLocaleEntity entity, UpdateItemTypeLocaleRequest request) {
        applyCommonFields(entity, request);
    }

    private void applyCommonFields(ItemTypeLocaleEntity entity, ItemTypeLocaleRequest request) {
        entity.setName(request.getName());
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setSortOrder(request.getSortOrder());
    }

    public ItemTypeLocaleDto toDto(ItemTypeLocaleEntity entity) {
        return ItemTypeLocaleDto.builder()
                .id(entity.getId())
                .localeId(entity.getLocaleEntity().getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .sortOrder(entity.getSortOrder())
                .build();
    }
}
