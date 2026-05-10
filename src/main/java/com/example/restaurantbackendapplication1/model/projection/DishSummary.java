package com.example.restaurantbackendapplication1.model.projection;

import org.springframework.beans.factory.annotation.Value;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface DishSummary {
    Long getId();

    @Value("#{target.menuCategoryEntity.id}")
    Long getMenuCategoryId();

    String getCode();
    Integer getSortOrder();
    Boolean getIsAvailable();
    Boolean getIsFeatured();
    Boolean getIsVeg();
}
