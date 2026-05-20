package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.itemcategory.CreateItemCategoryRequest;
import com.example.restaurantbackendapplication1.dto.request.itemcategory.UpdateItemCategoryRequest;
import com.example.restaurantbackendapplication1.dto.response.ItemCategoryResponse;
import com.example.restaurantbackendapplication1.model.entity.ItemCategoryEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemTypeEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.projection.ItemCategorySummary;

import java.util.Map;

public interface ItemCategoryService {
    SuccessResponse create(CreateItemCategoryRequest request,
                           ItemTypeEntity itemTypeEntity,
                           ItemCategoryEntity itemCategoryEntity,
                           Map<Long, LocaleEntity> localeEntityMap);

    ItemCategoryEntity getEntityById(Long itemTypeId, Long id);

    ItemCategoryEntity getEntityById(Long id);

    ItemCategoryResponse getById(Long itemTypeId, Long id);

    PaginatedResponse<ItemCategorySummary> getAll(Long itemTypeId, PaginatedRequest request);

    PaginatedResponse<ItemCategorySummary> getAllRoots(Long itemTypeId, PaginatedRequest request);

    PaginatedResponse<ItemCategorySummary> getAllSubCategories(Long itemTypeId, Long itemCategoryId, PaginatedRequest request);

    SuccessResponse update(ItemCategoryEntity entity,
                           UpdateItemCategoryRequest request);

    SuccessResponse delete(ItemCategoryEntity entity);
}
