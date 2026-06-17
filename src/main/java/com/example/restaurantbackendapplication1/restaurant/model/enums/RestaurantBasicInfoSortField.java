package com.example.restaurantbackendapplication1.restaurant.model.enums;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum RestaurantBasicInfoSortField {
    ID("id"),
    CODE("code"),
    SORT_ORDER("sortOrder"),
    CREATED_AT("createdAt");

    private final String fieldName;

    RestaurantBasicInfoSortField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public static Set<String> allowedFields() {
        return Arrays.stream(values())
                .map(RestaurantBasicInfoSortField::getFieldName)
                .collect(Collectors.toSet());
    }
}
