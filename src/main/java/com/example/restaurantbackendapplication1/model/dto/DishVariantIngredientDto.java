package com.example.restaurantbackendapplication1.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DishVariantIngredientDto {
    private Long id;
    private Long dishVariantId;
    private Long itemId;
    private BigDecimal quantity;
    private Long unitId;
    private Integer sortOrder;
}
