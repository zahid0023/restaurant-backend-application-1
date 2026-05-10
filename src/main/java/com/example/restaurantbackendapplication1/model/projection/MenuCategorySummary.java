package com.example.restaurantbackendapplication1.model.projection;

import org.springframework.beans.factory.annotation.Value;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface MenuCategorySummary {
    Long getId();

    @Value("#{target.menuEntity.id}")
    Long getMenuId();

    String getCode();
    Integer getSortOrder();
}
