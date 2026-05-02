package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.itemtypelocale.CreateItemTypeLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.itemtypelocale.UpdateItemTypeLocaleRequest;
import com.example.restaurantbackendapplication1.dto.response.ItemTypeLocaleResponse;
import com.example.restaurantbackendapplication1.model.entity.ItemTypeEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemTypeLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.projection.ItemTypeLocaleSummary;

public interface ItemTypeLocaleService {
    SuccessResponse create(ItemTypeEntity itemType, LocaleEntity locale, CreateItemTypeLocaleRequest request);

    ItemTypeLocaleEntity getEntityById(Long id, ItemTypeEntity itemType);

    ItemTypeLocaleResponse getById(Long id, ItemTypeEntity itemType);

    PaginatedResponse<ItemTypeLocaleSummary> getAll(ItemTypeEntity itemType, PaginatedRequest request);

    SuccessResponse update(ItemTypeLocaleEntity entity, LocaleEntity locale, UpdateItemTypeLocaleRequest request);

    SuccessResponse delete(ItemTypeLocaleEntity entity);
}
