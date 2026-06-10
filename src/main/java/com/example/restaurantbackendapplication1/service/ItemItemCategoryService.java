package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.model.entity.ItemCategoryEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemItemCategoryEntity;

public interface ItemItemCategoryService {
    SuccessResponse assign(ItemCategoryEntity itemCategoryEntity, ItemEntity itemEntity);

    ItemItemCategoryEntity getAssignment(ItemCategoryEntity itemCategoryEntity, ItemEntity itemEntity);

    SuccessResponse unassign(ItemItemCategoryEntity itemItemCategoryEntity);
}
