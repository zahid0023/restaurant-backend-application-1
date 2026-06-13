package com.example.restaurantbackendapplication1.item.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.item.dto.request.CreateItemRequest;
import com.example.restaurantbackendapplication1.item.dto.request.UpdateItemRequest;
import com.example.restaurantbackendapplication1.item.dto.response.ItemResponse;
import com.example.restaurantbackendapplication1.item.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.item.model.entity.ItemTypeEntity;
import com.example.restaurantbackendapplication1.item.model.projection.ItemSummary;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.unit.model.entity.UnitTypeEntity;

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

    PaginatedResponse<ItemSummary> getAll(Long itemTypeId, PaginatedRequest request);

    PaginatedResponse<ItemSummary> search(String query, PaginatedRequest request);

    SuccessResponse update(ItemEntity entity,
                           UpdateItemRequest request);

    SuccessResponse delete(Long id);
}
