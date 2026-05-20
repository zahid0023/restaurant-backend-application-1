package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.itemcategory.CreateItemCategoryRequest;
import com.example.restaurantbackendapplication1.dto.request.itemcategory.UpdateItemCategoryRequest;
import com.example.restaurantbackendapplication1.dto.request.itemcategorylocale.CreateItemCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.model.dto.ItemCategoryDto;
import com.example.restaurantbackendapplication1.model.dto.ItemCategoryLocaleDto;
import com.example.restaurantbackendapplication1.model.dto.ItemDto;
import com.example.restaurantbackendapplication1.model.entity.ItemCategoryEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemCategoryLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemTypeEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class ItemCategoryMapper {

    public static ItemCategoryEntity fromRequest(CreateItemCategoryRequest request,
                                                 ItemTypeEntity itemTypeEntity,
                                                 ItemCategoryEntity itemCategoryEntity,
                                                 Map<Long, LocaleEntity> localeEntityMap) {
        ItemCategoryEntity entity = new ItemCategoryEntity();
        entity.setItemTypeEntity(itemTypeEntity);
        entity.setItemCategoryEntity(itemCategoryEntity);
        entity.setCode(request.getCode());
        entity.setSortOrder(request.getSortOrder());

        if (request.getLocales() != null && !request.getLocales().isEmpty()) {
            Set<ItemCategoryLocaleEntity> locales = request.getLocales().stream()
                    .map(l -> ItemCategoryLocaleMapper.fromRequest(l, entity, localeEntityMap.get(l.getLocaleId())))
                    .collect(Collectors.toSet());

            entity.setItemCategoryLocaleEntities(locales);
        }
        return entity;
    }

    public static void update(ItemCategoryEntity entity,
                              UpdateItemCategoryRequest request) {
        entity.setSortOrder(request.getSortOrder());
    }

    public static ItemCategoryDto toDto(ItemCategoryEntity entity) {
        List<ItemCategoryLocaleDto> locales = entity.getItemCategoryLocaleEntities().stream()
                .map(ItemCategoryLocaleMapper::toDto)
                .toList();

        List<ItemCategoryDto> subCategories = entity.getSubCategoryEntities().stream()
                .filter(sc -> sc.getIsActive() && !sc.getIsDeleted())
                .map(child -> ItemCategoryDto.builder()
                        .id(child.getId())
                        .itemTypeId(child.getItemTypeEntity().getId())
                        .code(child.getCode())
                        .sortOrder(child.getSortOrder())
                        .locales(child.getItemCategoryLocaleEntities().stream()
                                .map(ItemCategoryLocaleMapper::toDto)
                                .toList())
                        .build())
                .toList();

        List<ItemDto> items = entity.getItemItemCategoryEntities().stream()
                .filter(iic -> iic.getIsActive() && !iic.getIsDeleted())
                .map(iic -> ItemMapper.toDto(iic.getItemEntity()))
                .toList();

        return ItemCategoryDto.builder()
                .id(entity.getId())
                .itemTypeId(entity.getItemTypeEntity().getId())
                .code(entity.getCode())
                .sortOrder(entity.getSortOrder())
                .locales(locales)
                .subCategories(subCategories)
                .items(items)
                .build();
    }
}
