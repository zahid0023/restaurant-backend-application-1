package com.example.restaurantbackendapplication1.inventory.service;

import com.example.restaurantbackendapplication1.commons.dto.request.PaginatedRequest;
import com.example.restaurantbackendapplication1.commons.dto.response.PaginatedResponse;
import com.example.restaurantbackendapplication1.commons.dto.response.SuccessResponse;
import com.example.restaurantbackendapplication1.inventory.dto.request.inventorytransaction.CreateInventoryTransactionRequest;
import com.example.restaurantbackendapplication1.inventory.dto.request.inventorytransaction.UpdateInventoryTransactionRequest;
import com.example.restaurantbackendapplication1.inventory.dto.response.InventoryTransactionResponse;
import com.example.restaurantbackendapplication1.inventory.model.entity.InventoryLocationEntity;
import com.example.restaurantbackendapplication1.inventory.model.entity.InventoryTransactionEntity;
import com.example.restaurantbackendapplication1.item.model.entity.ItemEntity;
import com.example.restaurantbackendapplication1.inventory.model.projection.InventoryTransactionSummary;

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
