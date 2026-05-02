package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.menuitem.CreateMenuItemRequest;
import com.example.restaurantbackendapplication1.dto.request.menuitem.UpdateMenuItemRequest;
import com.example.restaurantbackendapplication1.dto.request.menuitemlocale.MenuItemLocaleRequest;
import com.example.restaurantbackendapplication1.model.dto.MenuItemDto;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuItemEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuItemLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.MenuSectionEntity;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class MenuItemMapper {

    public static MenuItemEntity fromRequest(CreateMenuItemRequest request,
                                             MenuSectionEntity menuSectionEntity,
                                             Map<Long, LocaleEntity> localeEntityMap) {
        MenuItemEntity entity = new MenuItemEntity();
        entity.setMenuSectionEntity(menuSectionEntity);
        entity.setCode(request.getCode());
        entity.setSortOrder(request.getSortOrder());
        entity.setPrice(request.getPrice());
        entity.setIsAvailable(request.getIsAvailable() != null ? request.getIsAvailable() : true);
        entity.setIsFeatured(request.getIsFeatured() != null ? request.getIsFeatured() : false);
        entity.setIsVeg(request.getIsVeg());

        if (request.getLocales() != null) {
            Set<MenuItemLocaleEntity> localeEntities = request.getLocales().stream()
                    .map(l -> MenuItemLocaleMapper.fromRequest(l, entity, localeEntityMap.get(l.getLocaleId())))
                    .collect(Collectors.toSet());
            entity.setMenuItemLocaleEntities(localeEntities);
        }
        return entity;
    }

    public static void update(MenuItemEntity entity, UpdateMenuItemRequest request) {
        entity.setCode(request.getCode());
        entity.setSortOrder(request.getSortOrder());
        entity.setPrice(request.getPrice());
        entity.setIsAvailable(request.getIsAvailable() != null ? request.getIsAvailable() : true);
        entity.setIsFeatured(request.getIsFeatured() != null ? request.getIsFeatured() : false);
        entity.setIsVeg(request.getIsVeg());
    }

    public static MenuItemDto toDto(MenuItemEntity entity) {
        MenuItemDto dto = new MenuItemDto();
        dto.setId(entity.getId());
        dto.setMenuSectionId(entity.getMenuSectionEntity().getId());
        dto.setCode(entity.getCode());
        dto.setSortOrder(entity.getSortOrder());
        dto.setPrice(entity.getPrice());
        dto.setIsAvailable(entity.getIsAvailable());
        dto.setIsFeatured(entity.getIsFeatured());
        dto.setIsVeg(entity.getIsVeg());
        return dto;
    }
}
