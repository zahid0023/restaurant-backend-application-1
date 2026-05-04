package com.example.restaurantbackendapplication1.model.projection;

import org.springframework.beans.factory.annotation.Value;

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
