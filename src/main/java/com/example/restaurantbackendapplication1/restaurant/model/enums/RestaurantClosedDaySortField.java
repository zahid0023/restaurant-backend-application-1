package com.example.restaurantbackendapplication1.restaurant.model.enums;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum RestaurantClosedDaySortField {
    ID("id"),
    DAY_OF_WEEK("dayOfWeek"),
    CREATED_AT("createdAt");

    private final String fieldName;

    RestaurantClosedDaySortField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public static Set<String> allowedFields() {
        return Arrays.stream(values())
                .map(RestaurantClosedDaySortField::getFieldName)
                .collect(Collectors.toSet());
    }
}
