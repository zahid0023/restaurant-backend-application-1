package com.example.restaurantbackendapplication1.unit.model.mapper;

import com.example.restaurantbackendapplication1.unit.dto.request.unitlocale.CreateUnitLocaleRequest;
import com.example.restaurantbackendapplication1.unit.dto.request.unitlocale.UnitLocaleRequest;
import com.example.restaurantbackendapplication1.unit.dto.request.unitlocale.UpdateUnitLocaleRequest;
import com.example.restaurantbackendapplication1.unit.model.dto.UnitLocaleDto;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.unit.model.entity.UnitEntity;
import com.example.restaurantbackendapplication1.unit.model.entity.UnitLocaleEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UnitLocaleMapper {

    public UnitLocaleEntity create(CreateUnitLocaleRequest request,
                                   UnitEntity unitEntity,
                                   LocaleEntity localeEntity) {
        UnitLocaleEntity entity = new UnitLocaleEntity();
        entity.setUnitEntity(unitEntity);
        entity.setLocaleEntity(localeEntity);
        applyCommonFields(entity, request);
        return entity;
    }

    public void update(UnitLocaleEntity entity, UpdateUnitLocaleRequest request) {
        applyCommonFields(entity, request);
    }

    private void applyCommonFields(UnitLocaleEntity entity, UnitLocaleRequest request) {
        entity.setName(request.getName());
        entity.setDescription(request.getDescription() != null ? request.getDescription() : "");
        entity.setSortOrder(request.getSortOrder());
    }

    public UnitLocaleDto toDto(UnitLocaleEntity entity) {
        return UnitLocaleDto.builder()
                .id(entity.getId())
                .localeId(entity.getLocaleEntity().getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .sortOrder(entity.getSortOrder())
                .build();
    }
}
