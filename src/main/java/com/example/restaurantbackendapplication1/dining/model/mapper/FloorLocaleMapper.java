package com.example.restaurantbackendapplication1.dining.model.mapper;

import com.example.restaurantbackendapplication1.dining.dto.request.floorlocale.CreateFloorLocaleRequest;
import com.example.restaurantbackendapplication1.dining.dto.request.floorlocale.FloorLocaleRequest;
import com.example.restaurantbackendapplication1.dining.dto.request.floorlocale.UpdateFloorLocaleRequest;
import com.example.restaurantbackendapplication1.dining.model.dto.FloorLocaleDto;
import com.example.restaurantbackendapplication1.dining.model.entity.FloorEntity;
import com.example.restaurantbackendapplication1.dining.model.entity.FloorLocaleEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FloorLocaleMapper {

    public FloorLocaleEntity create(CreateFloorLocaleRequest request,
                                    FloorEntity floorEntity,
                                    LocaleEntity localeEntity) {
        FloorLocaleEntity entity = new FloorLocaleEntity();
        entity.setFloorEntity(floorEntity);
        entity.setLocaleEntity(localeEntity);
        applyCommonFields(entity, request);
        return entity;
    }

    public void update(FloorLocaleEntity entity, UpdateFloorLocaleRequest request) {
        applyCommonFields(entity, request);
    }

    private void applyCommonFields(FloorLocaleEntity entity, FloorLocaleRequest request) {
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setSortOrder(request.getSortOrder());
    }

    public FloorLocaleDto toDto(FloorLocaleEntity entity) {
        return FloorLocaleDto.builder()
                .id(entity.getId())
                .localeId(entity.getLocaleEntity().getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .sortOrder(entity.getSortOrder())
                .build();
    }
}
