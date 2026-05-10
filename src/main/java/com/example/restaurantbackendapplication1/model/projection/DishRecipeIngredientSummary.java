package com.example.restaurantbackendapplication1.model.projection;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface DishRecipeIngredientSummary {
    Long getId();

    @Value("#{target.dishRecipeEntity.id}")
    Long getDishRecipeId();

    @Value("#{target.itemEntity.id}")
    Long getItemId();

    BigDecimal getQuantity();
}
