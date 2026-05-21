package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.diningspacetypelocale.CreateDiningSpaceTypeLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.diningspacetypelocale.DiningSpaceTypeLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.diningspacetypelocale.UpdateDiningSpaceTypeLocaleRequest;
import com.example.restaurantbackendapplication1.model.dto.DiningSpaceTypeLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceTypeEntity;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceTypeLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DiningSpaceTypeLocaleMapper {

    public DiningSpaceTypeLocaleEntity create(CreateDiningSpaceTypeLocaleRequest request,
                                              DiningSpaceTypeEntity diningSpaceTypeEntity,
                                              LocaleEntity localeEntity) {
        DiningSpaceTypeLocaleEntity entity = new DiningSpaceTypeLocaleEntity();
        entity.setDiningSpaceTypeEntity(diningSpaceTypeEntity);
        entity.setLocaleEntity(localeEntity);
        applyCommonFields(entity, request);
        return entity;
    }

    public void update(DiningSpaceTypeLocaleEntity entity, UpdateDiningSpaceTypeLocaleRequest request) {
        applyCommonFields(entity, request);
    }

    private void applyCommonFields(DiningSpaceTypeLocaleEntity entity, DiningSpaceTypeLocaleRequest request) {
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setSortOrder(request.getSortOrder());
    }

    public DiningSpaceTypeLocaleDto toDto(DiningSpaceTypeLocaleEntity entity) {
        return DiningSpaceTypeLocaleDto.builder()
                .id(entity.getId())
                .localeId(entity.getLocaleEntity().getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .sortOrder(entity.getSortOrder())
                .build();
    }
}
