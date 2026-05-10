package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.diningspacelocale.DiningSpaceLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.diningspacelocale.UpdateDiningSpaceLocaleRequest;
import com.example.restaurantbackendapplication1.model.dto.DiningSpaceLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceEntity;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DiningSpaceLocaleMapper {

    public static DiningSpaceLocaleEntity fromRequest(
            DiningSpaceLocaleRequest request,
            DiningSpaceEntity diningSpaceEntity,
            LocaleEntity localeEntity) {
        DiningSpaceLocaleEntity entity = new DiningSpaceLocaleEntity();
        entity.setDiningSpaceEntity(diningSpaceEntity);
        entity.setLocaleEntity(localeEntity);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setSortOrder(request.getSortOrder());
        return entity;
    }

    public static void update(
            DiningSpaceLocaleEntity entity,
            UpdateDiningSpaceLocaleRequest request,
            LocaleEntity locale) {
        entity.setLocaleEntity(locale);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setSortOrder(request.getSortOrder());
    }

    public static DiningSpaceLocaleDto toDto(DiningSpaceLocaleEntity entity) {
        DiningSpaceLocaleDto dto = new DiningSpaceLocaleDto();
        dto.setId(entity.getId());
        dto.setLocaleId(entity.getLocaleEntity().getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setSortOrder(entity.getSortOrder());
        return dto;
    }
}
