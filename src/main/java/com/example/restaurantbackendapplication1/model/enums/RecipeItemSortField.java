package com.example.restaurantbackendapplication1.model.enums;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum RecipeItemSortField {
    ID("id"),
    CREATED_AT("createdAt");

    private final String fieldName;

    RecipeItemSortField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public static Set<String> allowedFields() {
        return Arrays.stream(values())
                .map(RecipeItemSortField::getFieldName)
                .collect(Collectors.toSet());
    }
}
