package com.example.restaurantbackendapplication1.dto.request.dishrecipeingredient;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DishRecipeIngredientRequest {

    @NotNull
    private Long itemId;

    private BigDecimal quantity;
}
