package com.example.restaurantbackendapplication1.model.projection;

import org.springframework.beans.factory.annotation.Value;

public interface DishRecipeSummary {
    Long getId();

    @Value("#{target.dishVariantEntity.id}")
    Long getDishVariantId();

    String getCode();
}
