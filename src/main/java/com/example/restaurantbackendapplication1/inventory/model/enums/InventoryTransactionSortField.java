package com.example.restaurantbackendapplication1.inventory.model.enums;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum InventoryTransactionSortField {
    ID("id"),
    TRANSACTION_TYPE("transactionType"),
    QUANTITY("quantity"),
    CREATED_AT("createdAt");

    private final String fieldName;

    InventoryTransactionSortField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public static Set<String> allowedFields() {
        return Arrays.stream(values())
                .map(InventoryTransactionSortField::getFieldName)
                .collect(Collectors.toSet());
    }
}
