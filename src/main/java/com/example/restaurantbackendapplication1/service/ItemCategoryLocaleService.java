package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.itemcategorylocale.CreateItemCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.itemcategorylocale.UpdateItemCategoryLocaleRequest;
import com.example.restaurantbackendapplication1.dto.response.ItemCategoryLocaleResponse;
import com.example.restaurantbackendapplication1.model.entity.ItemCategoryEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemCategoryLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.projection.ItemCategoryLocaleSummary;

public interface ItemCategoryLocaleService {
    SuccessResponse create(ItemCategoryEntity itemCategoryEntity, LocaleEntity locale, CreateItemCategoryLocaleRequest request);

    ItemCategoryLocaleEntity getEntityById(Long id, ItemCategoryEntity itemCategoryEntity);

    ItemCategoryLocaleResponse getById(Long id, ItemCategoryEntity itemCategoryEntity);

    PaginatedResponse<ItemCategoryLocaleSummary> getAll(ItemCategoryEntity itemCategoryEntity, PaginatedRequest request);

    SuccessResponse update(ItemCategoryLocaleEntity entity, LocaleEntity locale, UpdateItemCategoryLocaleRequest request);

    SuccessResponse delete(ItemCategoryLocaleEntity entity);
}
