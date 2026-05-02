package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.inventorylocationlocale.CreateInventoryLocationLocaleRequest;
import com.example.restaurantbackendapplication1.dto.request.inventorylocationlocale.UpdateInventoryLocationLocaleRequest;
import com.example.restaurantbackendapplication1.dto.response.InventoryLocationLocaleResponse;
import com.example.restaurantbackendapplication1.model.entity.InventoryLocationEntity;
import com.example.restaurantbackendapplication1.model.entity.InventoryLocationLocaleEntity;
import com.example.restaurantbackendapplication1.model.entity.LocaleEntity;
import com.example.restaurantbackendapplication1.model.projection.InventoryLocationLocaleSummary;

public interface InventoryLocationLocaleService {
    SuccessResponse create(InventoryLocationEntity inventoryLocationEntity, LocaleEntity localeEntity,
                           CreateInventoryLocationLocaleRequest request);

    InventoryLocationLocaleEntity getEntityById(Long id, InventoryLocationEntity inventoryLocationEntity);

    InventoryLocationLocaleResponse getById(Long id, InventoryLocationEntity inventoryLocationEntity);

    PaginatedResponse<InventoryLocationLocaleSummary> getAll(InventoryLocationEntity inventoryLocationEntity,
                                                             PaginatedRequest request);

    SuccessResponse update(InventoryLocationLocaleEntity entity, LocaleEntity localeEntity,
                           UpdateInventoryLocationLocaleRequest request);

    SuccessResponse delete(InventoryLocationLocaleEntity entity);
}
