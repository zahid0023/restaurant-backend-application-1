package com.example.restaurantbackendapplication1.dish.model.enums;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum DishVariantIngredientSortField {
    ID("id"),
    SORT_ORDER("sortOrder"),
    CREATED_AT("createdAt");

    private final String fieldName;

    DishVariantIngredientSortField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public static Set<String> allowedFields() {
        return Arrays.stream(values())
                .map(DishVariantIngredientSortField::getFieldName)
                .collect(Collectors.toSet());
    }
}
