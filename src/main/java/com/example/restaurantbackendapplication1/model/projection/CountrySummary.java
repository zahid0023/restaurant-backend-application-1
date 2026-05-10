package com.example.restaurantbackendapplication1.model.projection;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface CountrySummary {
    Long getId();

    String getCode();

    String getIso3Code();

    Integer getSortOrder();

    String getPhoneCode();
}
