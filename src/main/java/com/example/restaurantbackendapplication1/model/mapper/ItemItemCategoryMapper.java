package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.model.dto.ItemItemCategoryDto;
import com.example.restaurantbackendapplication1.model.entity.ItemCategoryEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemItemCategoryEntity;
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
                .itemId(entity.getItemEntity().getId())
                .itemCategoryId(entity.getItemCategoryEntity().getId())
                .build();
    }
}
