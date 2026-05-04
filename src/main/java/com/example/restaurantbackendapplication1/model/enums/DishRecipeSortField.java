package com.example.restaurantbackendapplication1.model.enums;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum DishRecipeSortField {
    ID("id"),
    CODE("code"),
    CREATED_AT("createdAt");

    private final String fieldName;

    DishRecipeSortField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public static Set<String> allowedFields() {
        return Arrays.stream(values())
                .map(DishRecipeSortField::getFieldName)
                .collect(Collectors.toSet());
    }
}
