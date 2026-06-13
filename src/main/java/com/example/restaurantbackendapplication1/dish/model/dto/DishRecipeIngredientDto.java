package com.example.restaurantbackendapplication1.dish.model.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DishRecipeIngredientDto {
    private Long id;
    private Long dishRecipeId;
    private Long itemId;
    private BigDecimal quantity;
    private Long unitId;
}
