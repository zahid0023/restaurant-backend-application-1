package com.example.restaurantbackendapplication1.model.projection;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

public interface DishVariantSummary {
    Long getId();

    @Value("#{target.dishEntity.id}")
    Long getDishId();

    String getCode();
    Integer getSortOrder();
    BigDecimal getPrice();
    Boolean getIsDefault();
}
