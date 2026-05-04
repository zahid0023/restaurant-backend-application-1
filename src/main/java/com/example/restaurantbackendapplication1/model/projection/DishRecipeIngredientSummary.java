package com.example.restaurantbackendapplication1.model.projection;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

public interface DishRecipeIngredientSummary {
    Long getId();

    @Value("#{target.dishRecipeEntity.id}")
    Long getDishRecipeId();

    @Value("#{target.itemEntity.id}")
    Long getItemId();

    BigDecimal getQuantity();
}
