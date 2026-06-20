package com.example.restaurantbackendapplication1.dish.model.enums;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum DishVariantImageSortField {
    ID("id"),
    SORT_ORDER("sortOrder"),
    CREATED_AT("createdAt");

    private final String fieldName;

    DishVariantImageSortField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public static Set<String> allowedFields() {
        return Arrays.stream(values())
                .map(DishVariantImageSortField::getFieldName)
                .collect(Collectors.toSet());
    }
}
