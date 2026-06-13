package com.example.restaurantbackendapplication1.dish.model.enums;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum DishVariantSortField {
    ID("id"),
    CODE("code"),
    SORT_ORDER("sortOrder"),
    PRICE("price"),
    CREATED_AT("createdAt");

    private final String fieldName;

    DishVariantSortField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public static Set<String> allowedFields() {
        return Arrays.stream(values())
                .map(DishVariantSortField::getFieldName)
                .collect(Collectors.toSet());
    }
}
