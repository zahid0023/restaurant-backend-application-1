package com.example.restaurantbackendapplication1.item.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.item.dto.request.itemtype.CreateItemTypeRequest;
import com.example.restaurantbackendapplication1.item.dto.request.itemtype.UpdateItemTypeRequest;
import com.example.restaurantbackendapplication1.item.dto.response.ItemTypeResponse;
import com.example.restaurantbackendapplication1.item.model.entity.ItemTypeEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.item.model.projection.ItemTypeSummary;

import java.util.Map;

public interface ItemTypeService {
    SuccessResponse create(CreateItemTypeRequest request,
                           Map<Long, LocaleEntity> localeEntityMap);

    ItemTypeEntity getEntityById(Long id);

    ItemTypeResponse getById(Long id);

    PaginatedResponse<ItemTypeSummary> getAll(PaginatedRequest request);

    SuccessResponse update(ItemTypeEntity entity, UpdateItemTypeRequest request);

    SuccessResponse delete(Long id);
}
