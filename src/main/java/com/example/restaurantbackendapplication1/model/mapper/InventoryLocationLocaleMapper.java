package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.inventorylocationlocale.InventoryLocationLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.inventorylocationlocale.UpdateInventoryLocationLocaleRequest;
import com.example.restaurantbackendapplication1.model.dto.InventoryLocationLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.InventoryLocationEntity;
import com.example.restaurantbackendapplication1.model.entity.InventoryLocationLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class InventoryLocationLocaleMapper {

    public static InventoryLocationLocaleEntity fromRequest(
            InventoryLocationLocaleRequest request,
            InventoryLocationEntity inventoryLocationEntity,
            LocaleEntity localeEntity) {
        InventoryLocationLocaleEntity entity = new InventoryLocationLocaleEntity();
        entity.setInventoryLocationEntity(inventoryLocationEntity);
        entity.setLocaleEntity(localeEntity);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setSortOrder(request.getSortOrder());
        return entity;
    }

    public static void update(
            InventoryLocationLocaleEntity entity,
            UpdateInventoryLocationLocaleRequest request,
            LocaleEntity localeEntity) {
        entity.setLocaleEntity(localeEntity);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setSortOrder(request.getSortOrder());
    }

    public static InventoryLocationLocaleDto toDto(InventoryLocationLocaleEntity entity) {
        return InventoryLocationLocaleDto.builder()
                .id(entity.getId())
                .localeId(entity.getLocaleEntity().getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .sortOrder(entity.getSortOrder())
                .build();
    }
}
