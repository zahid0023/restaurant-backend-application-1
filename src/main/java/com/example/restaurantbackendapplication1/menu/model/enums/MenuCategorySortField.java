package com.example.restaurantbackendapplication1.menu.model.enums;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum MenuCategorySortField {
    ID("id"),
    CODE("code"),
    SORT_ORDER("sortOrder"),
    CREATED_AT("createdAt");

    private final String fieldName;

    MenuCategorySortField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public static Set<String> allowedFields() {
        return Arrays.stream(values())
                .map(MenuCategorySortField::getFieldName)
                .collect(Collectors.toSet());
    }
}
