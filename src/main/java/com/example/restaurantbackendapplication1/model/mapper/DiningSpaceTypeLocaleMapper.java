package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.diningspacetypelocale.DiningSpaceTypeLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.diningspacetypelocale.UpdateDiningSpaceTypeLocaleRequest;
import com.example.restaurantbackendapplication1.model.dto.DiningSpaceTypeLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceTypeEntity;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceTypeLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DiningSpaceTypeLocaleMapper {

    public static DiningSpaceTypeLocaleEntity fromRequest(
            DiningSpaceTypeLocaleRequest request,
            DiningSpaceTypeEntity diningSpaceTypeEntity,
            LocaleEntity localeEntity) {
        DiningSpaceTypeLocaleEntity entity = new DiningSpaceTypeLocaleEntity();
        entity.setDiningSpaceTypeEntity(diningSpaceTypeEntity);
        entity.setLocaleEntity(localeEntity);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setSortOrder(request.getSortOrder());
        return entity;
    }

    public static void update(
            DiningSpaceTypeLocaleEntity entity,
            UpdateDiningSpaceTypeLocaleRequest request,
            LocaleEntity locale) {
        entity.setLocaleEntity(locale);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setSortOrder(request.getSortOrder());
    }

    public static DiningSpaceTypeLocaleDto toDto(DiningSpaceTypeLocaleEntity entity) {
        DiningSpaceTypeLocaleDto dto = new DiningSpaceTypeLocaleDto();
        dto.setId(entity.getId());
        dto.setLocaleId(entity.getLocaleEntity().getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setSortOrder(entity.getSortOrder());
        return dto;
    }
}
