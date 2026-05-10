package com.example.restaurantbackendapplication1.model.projection;

import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface DishVariantSummary {
    Long getId();

    @Value("#{target.dishEntity.id}")
    Long getDishId();

    String getCode();
    Integer getSortOrder();
    BigDecimal getPrice();
    Boolean getIsDefault();
}
