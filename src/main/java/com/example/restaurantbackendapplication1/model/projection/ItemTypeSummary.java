package com.example.restaurantbackendapplication1.model.projection;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface ItemTypeSummary {
    Long getId();
    String getCode();
    Boolean getIsConsumable();
    Integer getSortOrder();
}
