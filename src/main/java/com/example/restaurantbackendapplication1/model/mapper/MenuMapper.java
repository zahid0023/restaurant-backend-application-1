package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.menu.CreateMenuRequest;
import com.example.restaurantbackendapplication1.dto.request.menu.UpdateMenuRequest;
import com.example.restaurantbackendapplication1.model.dto.MenuDto;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuLocaleEntity;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class MenuMapper {

    public static MenuEntity fromRequest(CreateMenuRequest request, Map<Long, LocaleEntity> localeEntityMap) {
        MenuEntity entity = new MenuEntity();
        entity.setCode(request.getCode());
        entity.setSortOrder(request.getSortOrder());

        if (request.getLocales() != null) {
            Set<MenuLocaleEntity> localeEntities = request.getLocales().stream()
                    .map(l -> MenuLocaleMapper.fromRequest(l, entity, localeEntityMap.get(l.getLocaleId())))
                    .collect(Collectors.toSet());
            entity.setMenuLocaleEntities(localeEntities);
        }
        return entity;
    }

    public static void update(MenuEntity entity, UpdateMenuRequest request) {
        entity.setCode(request.getCode());
        entity.setSortOrder(request.getSortOrder());
    }

    public static MenuDto toDto(MenuEntity entity) {
        MenuDto dto = new MenuDto();
        dto.setId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setSortOrder(entity.getSortOrder());
        return dto;
    }
}
