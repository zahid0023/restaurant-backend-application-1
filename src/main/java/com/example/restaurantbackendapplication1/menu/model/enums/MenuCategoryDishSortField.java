package com.example.restaurantbackendapplication1.menu.model.enums;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum MenuCategoryDishSortField {
    ID("id"),
    CREATED_AT("createdAt");

    private final String fieldName;

    MenuCategoryDishSortField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public static Set<String> allowedFields() {
        return Arrays.stream(values())
                .map(MenuCategoryDishSortField::getFieldName)
                .collect(Collectors.toSet());
    }
}
