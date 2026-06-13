package com.example.restaurantbackendapplication1.menu.model.mapper;

import com.example.restaurantbackendapplication1.menu.dto.request.menutype.CreateMenuTypeRequest;
import com.example.restaurantbackendapplication1.menu.dto.request.menutype.UpdateMenuTypeRequest;
import com.example.restaurantbackendapplication1.menu.model.dto.MenuTypeDto;
import com.example.restaurantbackendapplication1.menu.model.dto.MenuTypeLocaleDto;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.menu.model.entity.MenuTypeEntity;
import com.example.restaurantbackendapplication1.menu.model.entity.MenuTypeLocaleEntity;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class MenuTypeMapper {

    public static MenuTypeEntity fromRequest(CreateMenuTypeRequest request, Map<Long, LocaleEntity> localeEntityMap) {
        MenuTypeEntity entity = new MenuTypeEntity();
        entity.setCode(request.getCode());
        entity.setSortOrder(request.getSortOrder());

        if (request.getLocales() != null) {
            Set<MenuTypeLocaleEntity> localeEntities = request.getLocales().stream()
                    .map(l -> MenuTypeLocaleMapper.fromRequest(l, entity, localeEntityMap.get(l.getLocaleId())))
                    .collect(Collectors.toSet());
            entity.setMenuTypeLocaleEntities(localeEntities);
        }
        return entity;
    }

    public static void update(MenuTypeEntity entity, UpdateMenuTypeRequest request) {
        entity.setSortOrder(request.getSortOrder());
    }

    public static MenuTypeDto toDto(MenuTypeEntity entity) {
        List<MenuTypeLocaleDto> locales = entity.getMenuTypeLocaleEntities().stream()
                .map(MenuTypeLocaleMapper::toDto)
                .collect(Collectors.toList());

        return MenuTypeDto.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .sortOrder(entity.getSortOrder())
                .locales(locales)
                .build();
    }
}
