package com.example.restaurantbackendapplication1.menu.model.enums;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum MenuItemLocaleSortField {
    ID("id"),
    NAME("name"),
    SORT_ORDER("sortOrder"),
    CREATED_AT("createdAt");

    private final String fieldName;

    MenuItemLocaleSortField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public static Set<String> allowedFields() {
        return Arrays.stream(values())
                .map(MenuItemLocaleSortField::getFieldName)
                .collect(Collectors.toSet());
    }
}
