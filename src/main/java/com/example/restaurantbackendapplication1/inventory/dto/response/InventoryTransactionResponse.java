package com.example.restaurantbackendapplication1.inventory.dto.response;

import com.example.restaurantbackendapplication1.inventory.model.dto.InventoryTransactionDto;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class InventoryTransactionResponse {
    private final InventoryTransactionDto inventoryTransaction;

    public InventoryTransactionResponse(InventoryTransactionDto inventoryTransaction) {
        this.inventoryTransaction = inventoryTransaction;
    }
}
