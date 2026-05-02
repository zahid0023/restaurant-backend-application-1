package com.example.restaurantbackendapplication1.model.mapper;

import com.example.restaurantbackendapplication1.dto.request.inventorytransaction.CreateInventoryTransactionRequest;
import com.example.restaurantbackendapplication1.dto.request.inventorytransaction.UpdateInventoryTransactionRequest;
import com.example.restaurantbackendapplication1.model.dto.InventoryTransactionDto;
import com.example.restaurantbackendapplication1.model.entity.InventoryLocationEntity;
import com.example.restaurantbackendapplication1.model.entity.InventoryTransactionEntity;
import com.example.restaurantbackendapplication1.model.entity.ItemEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class InventoryTransactionMapper {

    public static InventoryTransactionEntity fromRequest(CreateInventoryTransactionRequest request,
                                                         ItemEntity itemEntity,
                                                         InventoryLocationEntity inventoryLocationEntity) {
        InventoryTransactionEntity entity = new InventoryTransactionEntity();
        entity.setItemEntity(itemEntity);
        entity.setInventoryLocationEntity(inventoryLocationEntity);
        entity.setTransactionType(request.getTransactionType());
        entity.setQuantity(request.getQuantity());
        entity.setUnitCost(request.getUnitCost());
        entity.setTotalCost(request.getTotalCost());
        entity.setNotes(request.getNotes());
        return entity;
    }

    public static void update(InventoryTransactionEntity entity,
                              UpdateInventoryTransactionRequest request,
                              ItemEntity itemEntity,
                              InventoryLocationEntity inventoryLocationEntity) {
        entity.setItemEntity(itemEntity);
        entity.setInventoryLocationEntity(inventoryLocationEntity);
        entity.setTransactionType(request.getTransactionType());
        entity.setQuantity(request.getQuantity());
        entity.setUnitCost(request.getUnitCost());
        entity.setTotalCost(request.getTotalCost());
        entity.setNotes(request.getNotes());
    }

    public static InventoryTransactionDto toDto(InventoryTransactionEntity entity) {
        InventoryTransactionDto dto = new InventoryTransactionDto();
        dto.setId(entity.getId());
        dto.setItemId(entity.getItemEntity().getId());
        dto.setLocationId(entity.getInventoryLocationEntity().getId());
        dto.setTransactionType(entity.getTransactionType());
        dto.setQuantity(entity.getQuantity());
        dto.setUnitCost(entity.getUnitCost());
        dto.setTotalCost(entity.getTotalCost());
        dto.setNotes(entity.getNotes());
        return dto;
    }
}
