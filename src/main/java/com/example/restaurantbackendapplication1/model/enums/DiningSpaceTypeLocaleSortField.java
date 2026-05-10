package com.example.restaurantbackendapplication1.model.enums;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum DiningSpaceTypeLocaleSortField {
    ID("id"),
    NAME("name"),
    SORT_ORDER("sortOrder"),
    CREATED_AT("createdAt");

    private final String fieldName;

    DiningSpaceTypeLocaleSortField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public static Set<String> allowedFields() {
        return Arrays.stream(values())
                .map(DiningSpaceTypeLocaleSortField::getFieldName)
                .collect(Collectors.toSet());
    }
}
