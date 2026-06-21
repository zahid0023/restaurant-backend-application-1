package com.example.restaurantbackendapplication1.restaurant.model.enums;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum RestaurantOperatingHourSortField {
    ID("id"),
    DAY_OF_WEEK("dayOfWeek"),
    SEQUENCE_NO("sequenceNo"),
    CREATED_AT("createdAt");

    private final String fieldName;

    RestaurantOperatingHourSortField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public static Set<String> allowedFields() {
        return Arrays.stream(values())
                .map(RestaurantOperatingHourSortField::getFieldName)
                .collect(Collectors.toSet());
    }
}
