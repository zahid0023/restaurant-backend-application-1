package com.example.restaurantbackendapplication1.dish.model.projection;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface DishVariantImageSummary {
    Long getId();
    String getExternalId();
    String getUrl();
    String getCaption();
    Integer getSortOrder();
}
