package com.example.restaurantbackendapplication1.model.projection;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface UnitTypeSummary {
    Long getId();
    String getCode();
    Integer getSortOrder();
}
