package com.example.restaurantbackendapplication1.model.projection;

import org.springframework.beans.factory.annotation.Value;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface DishLocaleSummary {
    Long getId();

    @Value("#{target.localeEntity.id}")
    Long getLocaleId();

    String getName();
    Integer getSortOrder();
}
