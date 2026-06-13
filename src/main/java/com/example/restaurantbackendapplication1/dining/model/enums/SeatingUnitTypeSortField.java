package com.example.restaurantbackendapplication1.dining.model.enums;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum SeatingUnitTypeSortField {
    ID("id"),
    CODE("code"),
    SORT_ORDER("sortOrder"),
    CREATED_AT("createdAt");

    private final String fieldName;

    SeatingUnitTypeSortField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public static Set<String> allowedFields() {
        return Arrays.stream(values())
                .map(SeatingUnitTypeSortField::getFieldName)
                .collect(Collectors.toSet());
    }
}
