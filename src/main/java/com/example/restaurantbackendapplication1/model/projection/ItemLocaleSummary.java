package com.example.restaurantbackendapplication1.model.projection;

import org.springframework.beans.factory.annotation.Value;

public interface ItemLocaleSummary {
    Long getId();

    @Value("#{target.localeEntity.id}")
    Long getLocaleId();

    String getName();
    Integer getSortOrder();
}
