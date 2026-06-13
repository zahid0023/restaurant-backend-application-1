package com.example.restaurantbackendapplication1.item.model.projection;

import org.springframework.beans.factory.annotation.Value;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface ItemItemCategorySummary {
    Long getId();

    @Value("#{target.itemEntity.id}")
    Long getItemId();

    @Value("#{target.itemCategoryEntity.id}")
    Long getItemCategoryId();
}
