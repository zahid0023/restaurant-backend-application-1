package com.example.restaurantbackendapplication1.model.projection;

import com.example.restaurantbackendapplication1.model.enums.TransactionType;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

public interface InventoryTransactionSummary {
    Long getId();

    @Value("#{target.itemEntity.id}")
    Long getItemId();

    @Value("#{target.inventoryLocationEntity.id}")
    Long getLocationId();

    TransactionType getTransactionType();
    BigDecimal getQuantity();
}
