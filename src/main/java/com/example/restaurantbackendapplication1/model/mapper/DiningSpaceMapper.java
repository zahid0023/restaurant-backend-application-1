package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.diningspace.CreateDiningSpaceRequest;
import com.example.restaurantbackendapplication1.dto.request.diningspace.UpdateDiningSpaceRequest;
import com.example.restaurantbackendapplication1.model.dto.DiningSpaceDto;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceEntity;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.DiningSpaceTypeEntity;
import com.example.restaurantbackendapplication1.model.entity.FloorEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class DiningSpaceMapper {

    public static DiningSpaceEntity fromRequest(CreateDiningSpaceRequest request,
                                                DiningSpaceTypeEntity diningSpaceTypeEntity,
                                                FloorEntity floorEntity,
                                                Map<Long, LocaleEntity> localeEntityMap) {
        DiningSpaceEntity entity = new DiningSpaceEntity();
        entity.setDiningSpaceTypeEntity(diningSpaceTypeEntity);
        entity.setFloorEntity(floorEntity);
        entity.setCode(request.getCode());
        entity.setSortOrder(request.getSortOrder());
        entity.setCapacity(request.getCapacity());
        entity.setIsBookable(request.getIsBookable());

        if (request.getLocales() != null && !request.getLocales().isEmpty()) {
            Set<DiningSpaceLocaleEntity> locales = request.getLocales().stream()
                    .map(localeRequest -> DiningSpaceLocaleMapper.fromRequest(
                            localeRequest, entity, localeEntityMap.get(localeRequest.getLocaleId())))
                    .collect(Collectors.toSet());
            entity.setDiningSpaceLocaleEntities(locales);
        }
        return entity;
    }

    public static void update(DiningSpaceEntity entity,
                              UpdateDiningSpaceRequest request,
                              DiningSpaceTypeEntity diningSpaceTypeEntity,
                              FloorEntity floorEntity) {
        entity.setDiningSpaceTypeEntity(diningSpaceTypeEntity);
        entity.setFloorEntity(floorEntity);
        entity.setCode(request.getCode());
        entity.setSortOrder(request.getSortOrder());
        entity.setCapacity(request.getCapacity());
        entity.setIsBookable(request.getIsBookable());
    }

    public static DiningSpaceDto toDto(DiningSpaceEntity entity) {
        return DiningSpaceDto.builder()
                .id(entity.getId())
                .diningSpaceTypeId(entity.getDiningSpaceTypeEntity().getId())
                .floorId(entity.getFloorEntity() != null ? entity.getFloorEntity().getId() : null)
                .code(entity.getCode())
                .sortOrder(entity.getSortOrder())
                .capacity(entity.getCapacity())
                .isBookable(entity.getIsBookable())
                .build();
    }
}
