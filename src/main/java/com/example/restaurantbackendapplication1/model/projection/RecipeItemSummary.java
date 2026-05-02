package com.example.restaurantbackendapplication1.model.projection;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;

public interface RecipeItemSummary {
    Long getId();

    @Value("#{target.recipeEntity.id}")
    Long getRecipeId();

    @Value("#{target.itemEntity.id}")
    Long getItemId();

    BigDecimal getQuantity();
}
