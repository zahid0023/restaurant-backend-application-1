package com.example.restaurantbackendapplication1.imagehosting.enums;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum RestaurantImageHostingConfigSortField {
    ID("id"),
    PROVIDER("provider"),
    CREATED_AT("createdAt");

    private final String fieldName;

    RestaurantImageHostingConfigSortField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public static Set<String> allowedFields() {
        return Arrays.stream(values())
                .map(RestaurantImageHostingConfigSortField::getFieldName)
                .collect(Collectors.toSet());
    }
}
