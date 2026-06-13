package com.example.restaurantbackendapplication1.item.model.mapper;

import com.example.restaurantbackendapplication1.item.dto.request.itemcategory.CreateItemCategoryRequest;
import com.example.restaurantbackendapplication1.item.dto.request.itemcategory.UpdateItemCategoryRequest;
import com.example.restaurantbackendapplication1.item.model.dto.ItemCategoryDto;
import com.example.restaurantbackendapplication1.item.model.dto.ItemCategoryLocaleDto;
import com.example.restaurantbackendapplication1.item.model.dto.ItemSummaryDto;
import com.example.restaurantbackendapplication1.item.model.entity.ItemCategoryEntity;
import com.example.restaurantbackendapplication1.item.model.entity.ItemCategoryLocaleEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class ItemCategoryMapper {

    public static ItemCategoryEntity fromRequest(CreateItemCategoryRequest request,
                                                 ItemCategoryEntity itemCategoryEntity,
                                                 Map<Long, LocaleEntity> localeEntityMap) {
        ItemCategoryEntity entity = new ItemCategoryEntity();
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
                        .code(child.getCode())
                        .sortOrder(child.getSortOrder())
                        .locales(child.getItemCategoryLocaleEntities().stream()
                                .map(ItemCategoryLocaleMapper::toDto)
                                .toList())
                        .build())
                .toList();

        List<ItemSummaryDto> items = entity.getItemItemCategoryEntities().stream()
                .filter(itemItemCategoryEntity -> itemItemCategoryEntity.getIsActive() && !itemItemCategoryEntity.getIsDeleted())
                .map(itemItemCategoryEntity -> ItemMapper.toSummaryDto(itemItemCategoryEntity.getItemEntity()))
                .toList();

        return ItemCategoryDto.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .sortOrder(entity.getSortOrder())
                .locales(locales)
                .subCategories(subCategories)
                .items(items)
                .build();
    }
}
