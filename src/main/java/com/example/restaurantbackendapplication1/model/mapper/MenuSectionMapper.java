package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.menusection.CreateMenuSectionRequest;
import com.example.restaurantbackendapplication1.dto.request.menusection.UpdateMenuSectionRequest;
import com.example.restaurantbackendapplication1.dto.request.menusectionlocale.MenuSectionLocaleRequest;
import com.example.restaurantbackendapplication1.model.dto.MenuSectionDto;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuSectionEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuSectionLocaleEntity;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class MenuSectionMapper {

    public static MenuSectionEntity fromRequest(CreateMenuSectionRequest request,
                                                MenuEntity menuEntity,
                                                Map<Long, LocaleEntity> localeEntityMap) {
        MenuSectionEntity entity = new MenuSectionEntity();
        entity.setMenuEntity(menuEntity);
        entity.setCode(request.getCode());
        entity.setSortOrder(request.getSortOrder());

        if (request.getLocales() != null) {
            Set<MenuSectionLocaleEntity> localeEntities = request.getLocales().stream()
                    .map(l -> MenuSectionLocaleMapper.fromRequest(l, entity, localeEntityMap.get(l.getLocaleId())))
                    .collect(Collectors.toSet());
            entity.setMenuSectionLocaleEntities(localeEntities);
        }
        return entity;
    }

    public static void update(MenuSectionEntity entity, UpdateMenuSectionRequest request) {
        entity.setCode(request.getCode());
        entity.setSortOrder(request.getSortOrder());
    }

    public static MenuSectionDto toDto(MenuSectionEntity entity) {
        MenuSectionDto dto = new MenuSectionDto();
        dto.setId(entity.getId());
        dto.setMenuId(entity.getMenuEntity().getId());
        dto.setCode(entity.getCode());
        dto.setSortOrder(entity.getSortOrder());
        return dto;
    }
}
