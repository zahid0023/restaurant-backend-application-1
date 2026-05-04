package com.example.restaurantbackendapplication1.model.projection;

import org.springframework.beans.factory.annotation.Value;

public interface MenuCategorySummary {
    Long getId();

    @Value("#{target.menuEntity.id}")
    Long getMenuId();

    String getCode();
    Integer getSortOrder();
}
