package com.example.restaurantbackendapplication1.item.model.mapper;

import com.example.restaurantbackendapplication1.item.model.dto.ItemItemCategoryDto;
import com.example.restaurantbackendapplication1.item.model.entity.ItemCategoryEntity;
import com.example.restaurantbackendapplication1.item.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.item.model.entity.ItemItemCategoryEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ItemItemCategoryMapper {

    public static ItemItemCategoryEntity fromRequest(ItemEntity itemEntity, ItemCategoryEntity itemCategoryEntity) {
        ItemItemCategoryEntity entity = new ItemItemCategoryEntity();
        entity.setItemEntity(itemEntity);
        entity.setItemCategoryEntity(itemCategoryEntity);
        return entity;
    }

    public static ItemItemCategoryDto toDto(ItemItemCategoryEntity entity) {
        return ItemItemCategoryDto.builder()
                .id(entity.getId())
                .item(ItemMapper.toDto(entity.getItemEntity(), null, null))
                .itemCategory(ItemCategoryMapper.toDto(entity.getItemCategoryEntity()))
                .build();
    }
}
