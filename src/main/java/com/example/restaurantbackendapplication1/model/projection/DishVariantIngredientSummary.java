package com.example.restaurantbackendapplication1.model.projection;

import org.springframework.beans.factory.annotation.Value;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface DishVariantIngredientSummary {
    Long getId();

    @Value("#{target.dishVariant.id}")
    Long getDishVariantId();

    @Value("#{target.item.id}")
    Long getItemId();

    BigDecimal getQuantity();

    @Value("#{target.unitIt.id}")
    Long getUnitId();

    Integer getSortOrder();
}
