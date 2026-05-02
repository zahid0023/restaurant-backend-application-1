package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.model.entity.ItemCategoryEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemItemCategoryEntity;
import com.example.restaurantbackendapplication1.model.projection.ItemSummary;

public interface ItemItemCategoryService {
    SuccessResponse assign(ItemCategoryEntity itemCategoryEntity, ItemEntity itemEntity);

    PaginatedResponse<ItemSummary> getAllItems(ItemCategoryEntity itemCategoryEntity, PaginatedRequest request);

    ItemItemCategoryEntity getAssignment(ItemCategoryEntity itemCategoryEntity, ItemEntity itemEntity);

    SuccessResponse unassign(ItemItemCategoryEntity itemItemCategoryEntity);
}
