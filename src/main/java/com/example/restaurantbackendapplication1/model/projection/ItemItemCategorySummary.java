package com.example.restaurantbackendapplication1.model.projection;

import org.springframework.beans.factory.annotation.Value;

public interface ItemItemCategorySummary {
    Long getId();

    @Value("#{target.itemEntity.id}")
    Long getItemId();

    @Value("#{target.itemCategoryEntity.id}")
    Long getItemCategoryId();
}
