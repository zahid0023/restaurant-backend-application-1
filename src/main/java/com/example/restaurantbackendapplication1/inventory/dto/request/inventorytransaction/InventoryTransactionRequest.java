package com.example.restaurantbackendapplication1.inventory.dto.request.inventorytransaction;

import com.example.restaurantbackendapplication1.inventory.model.enums.TransactionType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class InventoryTransactionRequest {

    @NotNull
    private Long itemId;

    @NotNull
    private Long locationId;

    @NotNull
    private TransactionType transactionType;

    @NotNull
    private BigDecimal quantity;

    private BigDecimal unitCost;

    private BigDecimal totalCost;

    private String notes;
}
