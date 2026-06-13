package com.example.restaurantbackendapplication1.item.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.item.dto.request.itemcategory.CreateItemCategoryRequest;
import com.example.restaurantbackendapplication1.item.dto.request.itemcategory.UpdateItemCategoryRequest;
import com.example.restaurantbackendapplication1.item.dto.response.ItemCategoryResponse;
import com.example.restaurantbackendapplication1.item.model.entity.ItemCategoryEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.item.model.projection.ItemCategorySummary;

import java.util.Map;

public interface ItemCategoryService {
    SuccessResponse create(CreateItemCategoryRequest request,
                           ItemCategoryEntity itemCategoryEntity,
                           Map<Long, LocaleEntity> localeEntityMap);

    ItemCategoryEntity getEntityById(Long id);

    ItemCategoryResponse getById(Long id);

    PaginatedResponse<ItemCategorySummary> getAllRoots(PaginatedRequest request);

    PaginatedResponse<ItemCategorySummary> getAllSubCategories(Long itemCategoryId, PaginatedRequest request);

    SuccessResponse update(ItemCategoryEntity entity,
                           UpdateItemCategoryRequest request);

    SuccessResponse delete(ItemCategoryEntity entity);
}
