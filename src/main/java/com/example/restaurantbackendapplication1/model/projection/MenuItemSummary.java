package com.example.restaurantbackendapplication1.model.projection;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

public interface MenuItemSummary {
    Long getId();

    @Value("#{target.menuSectionEntity.id}")
    Long getMenuSectionId();

    String getCode();
    Integer getSortOrder();
    BigDecimal getPrice();
    Boolean getIsAvailable();
    Boolean getIsFeatured();
    Boolean getIsVeg();
}
