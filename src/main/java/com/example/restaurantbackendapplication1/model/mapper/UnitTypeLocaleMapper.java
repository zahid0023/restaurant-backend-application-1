package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.unittypelocale.CreateUnitTypeLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.unittypelocale.UnitTypeLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.unittypelocale.UpdateUnitTypeLocaleRequest;
import com.example.restaurantbackendapplication1.model.dto.UnitTypeLocaleDto;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitTypeEntity;
import com.example.restaurantbackendapplication1.model.entity.UnitTypeLocaleEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UnitTypeLocaleMapper {

    public UnitTypeLocaleEntity create(CreateUnitTypeLocaleRequest request,
                                       UnitTypeEntity unitTypeEntity,
                                       LocaleEntity localeEntity) {
        UnitTypeLocaleEntity entity = new UnitTypeLocaleEntity();
        entity.setUnitTypeEntity(unitTypeEntity);
        entity.setLocaleEntity(localeEntity);
        applyCommonFields(entity, request);
        return entity;
    }

    public void update(UnitTypeLocaleEntity entity, UpdateUnitTypeLocaleRequest request) {
        applyCommonFields(entity, request);
    }

    private void applyCommonFields(UnitTypeLocaleEntity entity, UnitTypeLocaleRequest request) {
        entity.setName(request.getName());
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setSortOrder(request.getSortOrder());
    }

    public UnitTypeLocaleDto toDto(UnitTypeLocaleEntity entity) {
        return UnitTypeLocaleDto.builder()
                .id(entity.getId())
                .localeId(entity.getLocaleEntity().getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .sortOrder(entity.getSortOrder())
                .build();
    }
}
