package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.item.CreateItemRequest;
import com.example.restaurantbackendapplication1.dto.request.item.UpdateItemRequest;
import com.example.restaurantbackendapplication1.dto.response.ItemResponse;
import com.example.restaurantbackendapplication1.model.entity.*;
import com.example.restaurantbackendapplication1.model.projection.ItemSummary;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ItemService {
    SuccessResponse create(CreateItemRequest request,
                           ItemTypeEntity itemTypeEntity,
                           UnitTypeEntity unitTypeEntity,
                           Map<Long, LocaleEntity> localeEntityMap);

    ItemEntity getEntityById(Long id);

    List<ItemEntity> getAll(Set<Long> ids);

    ItemResponse getById(Long id);

    PaginatedResponse<ItemSummary> getAll(PaginatedRequest request);

    SuccessResponse update(ItemEntity entity,
                           UpdateItemRequest request);

    SuccessResponse delete(Long id);
}
