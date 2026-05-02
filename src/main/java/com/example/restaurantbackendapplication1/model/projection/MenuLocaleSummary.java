package com.example.restaurantbackendapplication1.model.projection;

import org.springframework.beans.factory.annotation.Value;

public interface MenuLocaleSummary {
    Long getId();

    @Value("#{target.localeEntity.id}")
    Long getLocaleId();

    String getName();
    Integer getSortOrder();
}
