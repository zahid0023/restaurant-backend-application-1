package com.example.restaurantbackendapplication1.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.dto.request.inventorytransaction.CreateInventoryTransactionRequest;
import com.example.restaurantbackendapplication1.dto.request.inventorytransaction.UpdateInventoryTransactionRequest;
import com.example.restaurantbackendapplication1.dto.response.InventoryTransactionResponse;
import com.example.restaurantbackendapplication1.model.entity.InventoryLocationEntity;
import com.example.restaurantbackendapplication1.model.entity.InventoryTransactionEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.model.projection.InventoryTransactionSummary;

public interface InventoryTransactionService {
    SuccessResponse create(CreateInventoryTransactionRequest request,
                           ItemEntity itemEntity,
                           InventoryLocationEntity inventoryLocationEntity);

    InventoryTransactionEntity getEntityById(Long id);

    InventoryTransactionResponse getById(Long id);

    PaginatedResponse<InventoryTransactionSummary> getAll(PaginatedRequest request);

    SuccessResponse update(InventoryTransactionEntity entity,
                           UpdateInventoryTransactionRequest request,
                           ItemEntity itemEntity,
                           InventoryLocationEntity inventoryLocationEntity);

    SuccessResponse delete(Long id);
}
