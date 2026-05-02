package com.example.restaurantbackendapplication1.model.dto;

import com.example.restaurantbackendapplication1.model.enums.TransactionType;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class InventoryTransactionDto {
    private Long id;
    private Long itemId;
    private Long locationId;
    private TransactionType transactionType;
    private BigDecimal quantity;
    private BigDecimal unitCost;
    private BigDecimal totalCost;
    private String notes;
}
