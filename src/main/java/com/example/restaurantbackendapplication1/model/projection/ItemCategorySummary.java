package com.example.restaurantbackendapplication1.model.projection;

import org.springframework.beans.factory.annotation.Value;

public interface ItemCategorySummary {
    Long getId();

    @Value("#{target.itemTypeEntity.id}")
    Long getItemTypeId();

    String getCode();
    Integer getSortOrder();
}
