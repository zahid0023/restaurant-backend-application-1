package com.example.restaurantbackendapplication1.model.projection;

import org.springframework.beans.factory.annotation.Value;

public interface RecipeSummary {
    Long getId();

    @Value("#{target.menuItemEntity.id}")
    Long getMenuItemId();

    String getCode();
}
