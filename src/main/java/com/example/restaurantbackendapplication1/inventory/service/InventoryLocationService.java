package com.example.restaurantbackendapplication1.inventory.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.inventory.dto.request.inventorylocation.CreateInventoryLocationRequest;
import com.example.restaurantbackendapplication1.inventory.dto.request.inventorylocation.UpdateInventoryLocationRequest;
import com.example.restaurantbackendapplication1.inventory.dto.response.InventoryLocationResponse;
import com.example.restaurantbackendapplication1.inventory.model.entity.InventoryLocationEntity;
import com.example.restaurantbackendapplication1.locale.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.inventory.model.projection.InventoryLocationSummary;

import java.util.Map;

public interface InventoryLocationService {
    SuccessResponse create(CreateInventoryLocationRequest request, Map<Long, LocaleEntity> localeEntityMap);

    InventoryLocationEntity getEntityById(Long id);

    InventoryLocationResponse getById(Long id);

    PaginatedResponse<InventoryLocationSummary> getAll(PaginatedRequest request);

    SuccessResponse update(InventoryLocationEntity entity, UpdateInventoryLocationRequest request);

    SuccessResponse delete(Long id);
}
