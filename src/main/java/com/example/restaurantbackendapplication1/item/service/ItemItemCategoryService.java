package com.example.restaurantbackendapplication1.item.service;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.item.model.entity.ItemCategoryEntity;
import com.example.restaurantbackendapplication1.item.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.item.model.entity.ItemItemCategoryEntity;

public interface ItemItemCategoryService {
    SuccessResponse assign(ItemCategoryEntity itemCategoryEntity, ItemEntity itemEntity);

    ItemItemCategoryEntity getAssignment(ItemCategoryEntity itemCategoryEntity, ItemEntity itemEntity);

    SuccessResponse unassign(ItemItemCategoryEntity itemItemCategoryEntity);
}
